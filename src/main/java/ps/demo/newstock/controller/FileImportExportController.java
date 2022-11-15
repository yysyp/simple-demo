package ps.demo.newstock.controller;


import com.alibaba.excel.util.StringUtils;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.dto.response.DefaultResponse;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.exception.ServerApiException;
import ps.demo.newstock.constant.StkConstant;
import ps.demo.newstock.dto.NewStockDataDto;
import ps.demo.newstock.entity.NewStockData;
import ps.demo.newstock.service.HandleUpload;
import ps.demo.newstock.service.NewStockDataServiceImpl;
import ps.demo.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/newstock/file")
public class FileImportExportController {

    @Autowired
    private HandleUpload handleUpload;

    @Autowired
    private NewStockDataServiceImpl newStockDataServiceImpl;

    @Operation(summary = "File upload with multipart form data")
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public DefaultResponse uploadFile(@RequestParam("file") MultipartFile file,
                                      @RequestParam(value = "companyCode", required = true) String companyCode,
                                      @RequestParam(value = "companyName", required = true) String companyName,
                                      @RequestParam(value = "kemuType", required = true) String kemuType,
                                      HttpServletRequest req) {
        if (file == null) {
            throw new BadRequestException(CodeEnum.BAD_REQUEST, false, "File is required.");
        }
        try {
            String fileName = file.getOriginalFilename();
            log.info("new Stock upload file companyName={}, companyCode={}", companyName, companyCode);

            try (InputStream is = new BufferedInputStream(file.getInputStream())) {
                List<Object> sheet = MyExcelUtil.readMoreThan1000RowBySheet(is, null);
                List<NewStockDataDto> kemuRecords = handleUpload.handleDebtBenefitCashFlowExcel(sheet, kemuType, companyCode, companyName, fileName);

                handleUpload.findAndSetKemuEnglishName(kemuRecords, kemuType);

                //Calc pctInAssetOrRevenue
                handleUpload.calcPctInAssetOrRevenue(kemuRecords);

                List<NewStockDataDto> toInsert = filterExists(kemuRecords);
                batchSaveDto(toInsert);
                toInsert.clear();

                Calendar calendar = Calendar.getInstance();
                int nowYear = calendar.get(Calendar.YEAR);
                Integer[] months = new Integer[]{3, 6, 9, 12};
                Instant now = Instant.now();
                List<NewStockDataDto> toSaveDtos = new ArrayList<>();
                List<NewStockData> toSave = new ArrayList<>();
                for (Integer month : months) {
                    for (int year = 1991; year <= nowYear; year++) {

                        //Debt and Benefit and Cash reports data ready check
                        if (newStockDataServiceImpl.existsByAllKemuType(companyCode, year, month)) {
                            //calc coreProfit, coreProfitOnRevenue, revenueOnAssets, coreProfitOnAssets
                            calcCoreProfitOnAssetsEtc(companyCode, companyName, year, month, now, toSaveDtos);
                            batchSaveDto(toSaveDtos);
                            toSaveDtos.clear();

                            calcYoy(companyCode, year, month, toSave);
                            batchSave(toSave);
                            toSave.clear();

                            //calc yoy delta 's effect on coreProfitOnAssets
                            calcKemuYoyDeltaCoreProfitOnAssetsEffect(companyCode, companyName, year, month, toSave);
                            batchSave(toSave);
                            toSave.clear();
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, true, "--------->>File upload failed, ex:" + e.getMessage());
        }
        return DefaultResponse.success("Upload Success");
    }

    private List<NewStockDataDto> filterExists(List<NewStockDataDto> kemuRecords) {
        List<NewStockDataDto> toInsert = new ArrayList<>();
        Instant now = Instant.now();
        for (int i = 0; i < kemuRecords.size(); i++) {
            NewStockDataDto dto = kemuRecords.get(i);
            List<NewStockData> exist = newStockDataServiceImpl.findByCompanyCodePeriodKemu(dto.getCompanyCode(), dto.getPeriodYear(), dto.getPeriodMonth(), dto.getKemu());
            if (CollectionUtils.isEmpty(exist)) {
                dto.setCreatedOn(now);
                dto.setModifiedOn(now);
                dto.setCreatedBy("sys");
                dto.setModifiedBy("sys");
                dto.setIsActive(true);
                dto.setIsLogicalDeleted(false);
                toInsert.add(dto);

            }
        }
        return toInsert;
    }

    private void batchSaveDto(List<NewStockDataDto> toInsert) {
        batchSave(MyBeanUtil.copyAndConvertItems(toInsert, NewStockData.class));
//        List<List<NewStockDataDto>> lists
//                = Lists.partition(toInsert, 1000);
//        for (List<NewStockDataDto> subList : lists) {
//            newStockDataServiceImpl.saveAll(subList);
//        }
    }

    private void batchSave(List<NewStockData> toInsert) {
        List<List<NewStockData>> lists
                = Lists.partition(toInsert, 1000);
        for (List<NewStockData> subList : lists) {
            newStockDataServiceImpl.saveAll(subList);
        }
    }

    private void calcCoreProfitOnAssetsEtc(String companyCode, String companyName, Integer year, Integer month, Instant now, List<NewStockDataDto> toInsert) {

        List<NewStockData> list = newStockDataServiceImpl.findByCompanyCodePeriod(companyCode, year, month);
        Optional exist = list.stream().filter(e -> e.getKemuType().equals(StkConstant.calc)).findAny();
        if (exist.isPresent()) {
            return;
        }

        BigDecimal net_income = opGetKemuValue(findByKemuEn(list, StkConstant.NET_INCOME));
        BigDecimal revenue = opGetKemuValue(findByKemuEn(list, StkConstant.income_main));
        BigDecimal opCost = opGetKemuValue(findByKemuEn(list, StkConstant.cost_of_main_operation));
        BigDecimal sale = opGetKemuValue(findByKemuEn(list, StkConstant.SALE_EXPENSE));
        BigDecimal admin = opGetKemuValue(findByKemuEn(list, StkConstant.ADMINISTRATIVE_EXPENSE));
        BigDecimal fin = opGetKemuValue(findByKemuEn(list, StkConstant.FINANCIAL_EXPENSE));
        BigDecimal research = opGetKemuValue(findByKemuEn(list, StkConstant.RESEARCH_EXPENSE));
        BigDecimal tax = opGetKemuValue(findByKemuEn(list, StkConstant.TAX_AND_ADDITIONAL_EXPENSE));

        BigDecimal netAssets = opGetKemuValue(findByKemuEn(list, StkConstant.NET_ASSETS));
        BigDecimal assets = opGetKemuValue(findByKemuEn(list, StkConstant.total_assets));
        BigDecimal inventory = opGetKemuValue(findByKemuEn(list, StkConstant.INVENTORY));
        BigDecimal liabilities = opGetKemuValue(findByKemuEn(list, StkConstant.TOTAL_LIABILITIES));

        BigDecimal sale_cash = opGetKemuValue(findByKemuEn(list, StkConstant.SALE_PRODUCT_PROVIDE_SERVICE_RECEIVED_CASH));
        BigDecimal net_op_cash = opGetKemuValue(findByKemuEn(list, StkConstant.NET_OPERATION_GENERATED_CASH));
        BigDecimal ending_cash = opGetKemuValue(findByKemuEn(list, StkConstant.ENDING_CASH_EQUIVALENT_VALUE));


        if (fin.compareTo(BigDecimal.ZERO) < 0) {
            fin = BigDecimal.ZERO;
        }
        BigDecimal coreProfit = revenue.subtract(opCost).subtract(sale).subtract(admin).subtract(fin)
                .subtract(research).subtract(tax);

        NewStockDataDto dto = constructNewStockDataDto(companyCode, companyName, now, month, year, "核心利润", StkConstant.coreProfit, coreProfit);
        toInsert.add(dto);

        BigDecimal coreProfitOnRevenue = BigDecimal.ZERO;
        if (revenue.compareTo(BigDecimal.ZERO) != 0) {
            coreProfitOnRevenue = coreProfit.divide(revenue, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year, "核心利润/营收", StkConstant.coreProfitOnRevenue, coreProfitOnRevenue);
        toInsert.add(dto);

        BigDecimal revenueOnAssets = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            revenueOnAssets = revenue.divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year, "营收/总资产", StkConstant.revenueOnAssets, revenueOnAssets);
        toInsert.add(dto);

        BigDecimal coreProfitOnAssets = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            coreProfitOnAssets = coreProfit.divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year, "核心利润/总资产", StkConstant.coreProfitOnAssets, coreProfitOnAssets);
        toInsert.add(dto);


        //asset_liability_ratio
        BigDecimal asset_liability_ratio = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            asset_liability_ratio = liabilities.divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "资产负债率:总负债/总资产", StkConstant.asset_liability_ratio, asset_liability_ratio);
        toInsert.add(dto);


        //gross_profit = "gross_profit";
        BigDecimal gross_profit = BigDecimal.ZERO;
        if (revenue.compareTo(BigDecimal.ZERO) != 0) {
            gross_profit = revenue.subtract(opCost).divide(revenue, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "毛利率(营业收入-营业成本)/营业成本", StkConstant.gross_profit, gross_profit);
        toInsert.add(dto);

        //inventory_turnover
        BigDecimal inventory_turnover = BigDecimal.ZERO;
        if (inventory.compareTo(BigDecimal.ZERO) != 0) {
            inventory_turnover = opCost.divide(inventory, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "存货周转率：营业成本/存货", StkConstant.inventory_turnover, inventory_turnover);
        toInsert.add(dto);


        //return_on_equity = "return_on_equity";
        BigDecimal return_on_equity = BigDecimal.ZERO;
        if (netAssets.compareTo(BigDecimal.ZERO) != 0) {
            return_on_equity = net_income.divide(netAssets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "ROE净资产收益率", StkConstant.return_on_equity, return_on_equity);
        toInsert.add(dto);


        //expenses_on_income = "expenses_on_income";
        BigDecimal expenses_on_income = BigDecimal.ZERO;
        if (revenue.compareTo(BigDecimal.ZERO) != 0) {
            expenses_on_income = sale.add(admin).add(fin).add(research).divide(revenue, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "费用率:销管研财/营业总收入", StkConstant.expenses_on_income, expenses_on_income);
        toInsert.add(dto);

        //net_operating_cash_on_net_profit = "net_operating_cash_on_net_profit";
        BigDecimal net_operating_cash_on_net_profit = BigDecimal.ZERO;
        if (net_income.compareTo(BigDecimal.ZERO) != 0) {
            net_operating_cash_on_net_profit = net_op_cash.divide(net_income, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "经营现金流净额/净利润", StkConstant.net_operating_cash_on_net_profit, net_operating_cash_on_net_profit);
        toInsert.add(dto);

        //sales_cash_on_income = "sales_cash_on_income";
        BigDecimal sales_cash_on_income = BigDecimal.ZERO;
        if (revenue.compareTo(BigDecimal.ZERO) != 0) {
            sales_cash_on_income = sale_cash.divide(revenue, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "销售现金/营收", StkConstant.sales_cash_on_income, sales_cash_on_income);
        toInsert.add(dto);

        //heavy_assets_on_assets = "heavy_assets_on_assets";
        BigDecimal heavy_assets_on_assets = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal heavyassets = sumKemuByKeywordMatch(StkConstant.debt, list, "土地", "房地产", "固定资产", "在建工程", "生物资产", "油气资产");
            heavy_assets_on_assets = heavyassets.divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "重资产/总资产", StkConstant.heavy_assets_on_assets, heavy_assets_on_assets);
        toInsert.add(dto);

        //receivables_on_assets = "receivables_on_assets";
        BigDecimal receivables_on_assets = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal allys = sumKemuByKeywordMatch(StkConstant.debt, list, "应收");
            BigDecimal pj = sumKemuByKeywordMatch(StkConstant.debt, list, "应收票据");
            receivables_on_assets = allys.subtract(pj).divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "应收/总资产", StkConstant.receivables_on_assets, receivables_on_assets);
        toInsert.add(dto);

        //financial_assets_on_assets = "financial_assets_on_assets";
        BigDecimal financial_assets_on_assets = BigDecimal.ZERO;
        if (assets.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal finassets = sumKemuByKeywordMatch(StkConstant.debt, list, "金融资产", "投资", "持有待售");
            financial_assets_on_assets = finassets.divide(assets, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "金融资产/总资产", StkConstant.financial_assets_on_assets, financial_assets_on_assets);
        toInsert.add(dto);

        //ending_cash_on_interest_bearing_liabilities = "ending_cash_on_interest_bearing_liabilities";
        BigDecimal ending_cash_on_interest_bearing_liabilities = BigDecimal.ZERO;
        //短期借款+长期借款+应付债券+一年内到期的非流动性负债+一年内到期的融资租赁负债+融资租赁负债
        BigDecimal interestBearingLiabilities = sumKemuByKeywordMatch(StkConstant.debt, list, "借款", "应付债券", "一年内到期的非流动性负债", "融资租赁负债");
        if (interestBearingLiabilities.compareTo(BigDecimal.ZERO) != 0) {
            ending_cash_on_interest_bearing_liabilities = ending_cash.divide(interestBearingLiabilities, 4, BigDecimal.ROUND_HALF_UP);
        }
        dto = constructNewStockDataDto(companyCode, companyName, now, month, year
                , "期末现金/有息负债", StkConstant.ending_cash_on_interest_bearing_liabilities, ending_cash_on_interest_bearing_liabilities);
        toInsert.add(dto);

    }

    private void calcKemuYoyDeltaCoreProfitOnAssetsEffect(String companyCode, String companyName, Integer year, Integer month, List<NewStockData> toUpdate) {

        List<NewStockData> list = newStockDataServiceImpl.findByCompanyCodePeriod(companyCode, year, month);
        Optional exist = list.stream().filter(e -> e.getCoreProfitOnAssetEffect() != null).findAny();
        if (exist.isPresent()) {
            return;
        }

        BigDecimal coreProfit = opGetKemuValue(findByKemuEn(list, StkConstant.coreProfit));
        BigDecimal assets = opGetKemuValue(findByKemuEn(list, StkConstant.total_assets));
        BigDecimal coreProfitOnAssets = opGetKemuValue(findByKemuEn(list, StkConstant.coreProfitOnAssets));
        for (NewStockData newStockData : list) {
            BigDecimal yoy = newStockData.getYoy();
            String kemu = newStockData.getKemu();
            String kemuType = newStockData.getKemuType();
            if (kemuType == null
                    || kemuType.equals(StkConstant.calc)
                    || kemuType.equals(StkConstant.cash)
                    || yoy == null || yoy.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            BigDecimal kemuValue = newStockData.getKemuValue();
            BigDecimal denominator = yoy.add(BigDecimal.ONE);
            BigDecimal delta = BigDecimal.ZERO;
            if (denominator.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal oldKemuValue = kemuValue.divide(denominator, 4, BigDecimal.ROUND_HALF_UP);
                delta = kemuValue.subtract(oldKemuValue);
            }
            if (StkConstant.debt.equals(kemuType)) {
                BigDecimal fm = assets.subtract(delta);
                BigDecimal deltaCoreProfitOnAssets = BigDecimal.ZERO;
                if (fm.compareTo(BigDecimal.ZERO) != 0) {
                    deltaCoreProfitOnAssets = coreProfitOnAssets.subtract(
                            coreProfit.divide(fm, 4, BigDecimal.ROUND_HALF_UP));
                }
                newStockData.setCoreProfitOnAssetEffect(deltaCoreProfitOnAssets);
                toUpdate.add(newStockData);

            } else if (StkConstant.benefit.equals(kemuType)) {
                if (assets.compareTo(BigDecimal.ZERO) == 0) {
                    newStockData.setCoreProfitOnAssetEffect(BigDecimal.ZERO);
                    toUpdate.add(newStockData);
                    continue;
                }
                //收入，收益，利润，利得；支出，成本，费用，损失，税，
                BigDecimal fz = BigDecimal.ZERO;
                ;
                if (kemu.contains("收入") || kemu.contains("收益")
                        || kemu.contains("利润")
                        || kemu.contains("利得")) {
                    fz = coreProfit.subtract(delta);
                } else {
                    fz = coreProfit.add(delta);
                }
                BigDecimal deltaCoreProfitOnAssets = coreProfitOnAssets.subtract(
                        fz.divide(assets, 4, BigDecimal.ROUND_HALF_UP));
                newStockData.setCoreProfitOnAssetEffect(deltaCoreProfitOnAssets);
                toUpdate.add(newStockData);
            }
        }


    }

    private NewStockDataDto constructNewStockDataDto(String companyCode, String companyName, Instant now, Integer pm, int year, String kemu, String kemuEn, BigDecimal value) {
        NewStockDataDto dto = new NewStockDataDto();
        dto.setKemuType(StkConstant.calc);
        dto.setCompanyCode(companyCode);
        dto.setCompanyName(companyName);
        dto.setRawKemu(kemu);
        dto.setKemu(kemu);
        dto.setKemuEn(kemuEn);
        dto.setRawKemuValue(value + "");
        dto.setKemuValue(value);
        dto.setPeriodYear(year);
        dto.setPeriodMonth(pm);
        dto.setCreatedOn(now);
        dto.setModifiedOn(now);
        dto.setCreatedBy("sys");
        dto.setModifiedBy("sys");
        dto.setIsActive(true);
        dto.setIsLogicalDeleted(false);
        return dto;
    }

    public BigDecimal opGetKemuValue(NewStockData newStockData) {
        if (newStockData == null || newStockData.getKemuValue() == null) {
            return new BigDecimal("0");
        }
        return newStockData.getKemuValue();
    }

    public NewStockData findByKemuEn(List<NewStockData> list, String kemuEn) {
        Optional<NewStockData> optionalNewStockData = list.stream().filter(e -> kemuEn.equals(e.getKemuEn())).findAny();
        if (optionalNewStockData.isPresent()) {
            return optionalNewStockData.get();
        }
        return null;
    }

    public BigDecimal sumKemuByKeywordMatch(String kemuType, List<NewStockData> list, String... keywords) {
        BigDecimal result = list.stream().filter(e -> {
            if (!kemuType.equals(e.getKemuType())) {
                return false;
            }
            for (String k : keywords) {
                if (e.getKemu().contains(k)) {
                    return true;
                }
            }
            return false;
        }).map(e -> e.getKemuValue()).reduce(BigDecimal.ZERO, (p, q) -> p.add(q));
        return result;
    }

    private void calcYoy(String companyCode, Integer year, Integer month, List<NewStockData> toSave) {
        int previousYear = year - 1;
        List<NewStockData> nullYoyList = newStockDataServiceImpl.findByCompanyCodePeriodWithNullYoy(companyCode, year, month);
        for (NewStockData stockData : nullYoyList) {
            List<NewStockData> previousStocks = newStockDataServiceImpl.findByCompanyCodePeriodKemu(companyCode, previousYear, month, stockData.getKemu());
            if (CollectionUtils.isEmpty(previousStocks)) {
                continue;
            }
            NewStockData previousStock = previousStocks.get(0);

            BigDecimal yoy = BigDecimal.ZERO;
            if (previousStock.getKemuValue().compareTo(BigDecimal.ZERO) != 0) {
                yoy = stockData.getKemuValue().subtract(previousStock.getKemuValue()).divide(
                        previousStock.getKemuValue(), 4, BigDecimal.ROUND_HALF_UP);
            }
            if (yoy.compareTo(BigDecimal.ZERO) == -1) {
                stockData.setFlag(-1);
            }
            stockData.setYoy(yoy);
            toSave.add(stockData);
        }

    }


    //
    @GetMapping("/export")
    public void exportFile(@RequestParam(value = "companyCode", required = true) String companyCode,
                           @RequestParam(value = "fromYear", required = true, defaultValue = "2000") Integer fromYear,
                           @RequestParam(value = "month", required = true, defaultValue = "12") Integer month
    ) throws Exception {
        //line, column
        List<List<Object>> lines = new ArrayList<>();
        List<String> lineHeaders = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        for (int year = fromYear; year <= nowYear; year++) {
            List<NewStockData> newStockDataList = newStockDataServiceImpl.findByCompanyCodePeriod(companyCode, year, month);
            if (CollectionUtils.isEmpty(newStockDataList)) {
                continue;
            }
            if (lineHeaders.isEmpty()) {
                lineHeaders = getLineHeaders(newStockDataList);
            }
            convertAndPutIn(newStockDataList, lines, lineHeaders);
        }

        byte[] bs = null;
        try (InputStream in = handleUpload.exportFile(companyCode, lines)) {
            bs = IOUtils.toByteArray(in);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ignore " + e.getMessage(), e);
            throw e;
        }
        if (bs != null) {
            HttpServletResponse response = MyRequestContextUtil.getResponse();
            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode("export-" + companyCode + "-" + MyTimeUtil.getNowStr() + ".xlsx", "UTF-8"));
            response.addHeader("Content-Length", "" + bs.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.flushBuffer();
            //IOUtils.copy(in, response.getOutputStream());
            IOUtils.write(bs, response.getOutputStream());
        }

    }


    private List<String> getLineHeaders(List<NewStockData> newStockDataList) {
        List<String> lineHeaders = new ArrayList<>();
        if (CollectionUtils.isEmpty(newStockDataList)) {
            return lineHeaders;
        }
        Map<String, List<NewStockData>> mapList = newStockDataList.stream().collect(Collectors.groupingBy(e -> e.getKemuType()));
        lineHeaders.add("");
        lineHeaders.add(StkConstant.KEMU_TIME);
        List<NewStockData> calcList = mapList.get(StkConstant.calc);
        for (NewStockData newStockData : calcList) {
            lineHeaders.add(newStockData.getRawKemu());
        }
        lineHeaders.add("");
        lineHeaders.add("");
        lineHeaders.add(StkConstant.KEMU_TIME);
        List<NewStockData> debtList = mapList.get(StkConstant.debt);
        for (NewStockData newStockData : debtList) {
            lineHeaders.add(newStockData.getRawKemu());
        }
        lineHeaders.add("");
        lineHeaders.add("");
        lineHeaders.add(StkConstant.KEMU_TIME);
        List<NewStockData> benefitList = mapList.get(StkConstant.benefit);
        for (NewStockData newStockData : benefitList) {
            lineHeaders.add(newStockData.getRawKemu());
        }
        lineHeaders.add("");
        lineHeaders.add("");
        lineHeaders.add(StkConstant.KEMU_TIME);
        List<NewStockData> cashList = mapList.get(StkConstant.cash);
        for (NewStockData newStockData : cashList) {
            lineHeaders.add(newStockData.getRawKemu());
        }


        return lineHeaders;
    }

    private void convertAndPutIn(List<NewStockData> newStockDataList, List<List<Object>> lines, List<String> lineHeaders) {
        if (lines.isEmpty()) {
            for (String rawKemu : lineHeaders) {
                List<Object> line = new ArrayList<>();
                line.add(rawKemu);
                lines.add(line);
            }
        }
        for (List line : lines) {
            String rawKemu = line.get(0)+"";
            if (StringUtils.isEmpty(rawKemu)) {
                continue;
            }
            if (rawKemu.equals(StkConstant.KEMU_TIME)) {
                NewStockData d = newStockDataList.get(0);
                String yearMonth = d.getPeriodYear()+"-"+d.getPeriodMonth();
                line.add(yearMonth);
                line.add("Yoy");
                line.add("percentInAssetOrRevenue");
                line.add("CoreProfitOnAssetEffectByYoyDelta");
                continue;
            }
            Optional<NewStockData> newStockData = newStockDataList.stream().filter(e -> e.getRawKemu().equals(rawKemu)).findAny();
            if (!newStockData.isPresent()) {
                continue;
            }
            NewStockData nsd = newStockData.get();
            line.add(nsd.getKemuValue());
            line.add(nsd.getYoy());
            line.add(nsd.getPctInAssetOrRevenue());
            line.add(nsd.getCoreProfitOnAssetEffect());
        }
    }

}
