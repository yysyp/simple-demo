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
import ps.demo.util.MyExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

                //Calc pctInAssetOrRevenue
                handleUpload.calcPctInAssetOrRevenue(kemuRecords);

                //calc coreProfit, coreProfitOnRevenue

                List<NewStockDataDto> toInsert = filterExists(kemuRecords);

                batchSave(toInsert);

                //calc coreProfit, coreProfitOnRevenue, revenueOnAssets, coreProfitOnAssets
                calcCoreProfitOnAssetsEtc(companyCode, companyName);

                calcYoy(companyCode);

                //calc yoy delta 's effect on coreProfitOnAssets
                //calcCoreProfitOnAssetsEtc(companyCode, companyName);
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

    private void batchSave(List<NewStockDataDto> toInsert) {
        List<List<NewStockDataDto>> lists
                = Lists.partition(toInsert, 1000);
        for (List<NewStockDataDto> subList : lists) {
            newStockDataServiceImpl.saveAll(subList);
        }
    }

    private void calcCoreProfitOnAssetsEtc(String companyCode, String companyName) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        Integer[] months = new Integer[] {3, 6, 9, 12};
        List<NewStockDataDto> toInsert = new ArrayList<>();
        Instant now = Instant.now();
        for (Integer pm : months) {
            for (int year = 1991; year <= nowYear; year++) {
                int previousYear = year - 1;
                List<NewStockData> list = newStockDataServiceImpl.findByCompanyCodePeriod(companyCode, year, pm);
                Optional exist = list.stream().filter(e -> e.getKemuType().equals(StkConstant.calc)).findAny();
                if (exist.isPresent()) {
                    return;
                }

                BigDecimal revenue = opGetKemuValue(findByKemuEn(list, StkConstant.income_main));
                BigDecimal opCost = opGetKemuValue(findByKemuEn(list, StkConstant.cost_of_main_operation));
                BigDecimal sale = opGetKemuValue(findByKemuEn(list, StkConstant.SALE_EXPENSE));
                BigDecimal admin = opGetKemuValue(findByKemuEn(list, StkConstant.ADMINISTRATIVE_EXPENSE));
                BigDecimal fin = opGetKemuValue(findByKemuEn(list, StkConstant.FINANCIAL_EXPENSE));
                BigDecimal research = opGetKemuValue(findByKemuEn(list, StkConstant.RESEARCH_EXPENSE));
                BigDecimal tax = opGetKemuValue(findByKemuEn(list, StkConstant.TAX_AND_ADDITIONAL_EXPENSE));

                BigDecimal assets = opGetKemuValue(findByKemuEn(list, StkConstant.total_assets));

                if (fin.compareTo(BigDecimal.ZERO) < 0) {
                    fin = BigDecimal.ZERO;
                }
                BigDecimal coreProfit = revenue.subtract(opCost).subtract(sale).subtract(admin).subtract(fin)
                        .subtract(research).subtract(tax);

                NewStockDataDto dto = constructNewStockDataDto(companyCode, companyName, now, pm, year, "核心利润", StkConstant.coreProfit, coreProfit);
                toInsert.add(dto);

                BigDecimal coreProfitOnRevenue = BigDecimal.ZERO;
                if (revenue.compareTo(BigDecimal.ZERO) != 0) {
                    coreProfitOnRevenue = coreProfit.divide(revenue, BigDecimal.ROUND_HALF_UP, 4);
                }
                dto = constructNewStockDataDto(companyCode, companyName, now, pm, year, "核心利润/营收", StkConstant.coreProfitOnRevenue, coreProfitOnRevenue);
                toInsert.add(dto);

                BigDecimal revenueOnAssets = BigDecimal.ZERO;
                if (assets.compareTo(BigDecimal.ZERO) != 0) {
                    revenueOnAssets = revenue.divide(assets, BigDecimal.ROUND_HALF_UP, 5);
                }
                dto = constructNewStockDataDto(companyCode, companyName, now, pm, year, "营收/总资产", StkConstant.revenueOnAssets, revenueOnAssets);
                toInsert.add(dto);

                BigDecimal coreProfitOnAssets = BigDecimal.ZERO;
                if (assets.compareTo(BigDecimal.ZERO) != 0) {
                    coreProfitOnAssets = coreProfit.divide(assets, BigDecimal.ROUND_HALF_UP, 5);
                }
                dto = constructNewStockDataDto(companyCode, companyName, now, pm, year, "核心利润/总资产", StkConstant.coreProfitOnAssets, coreProfitOnAssets);
                toInsert.add(dto);

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
        dto.setRawKemuValue(value+"");
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

    private void calcYoy(String companyCode) {
        //Calc and fill Yoy
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        Integer[] months = new Integer[] {3, 6, 9, 12};
        for (Integer pm : months) {
            for (int year = 1991; year <= nowYear; year++) {
                int previousYear = year - 1;
                List<NewStockData> nullYoyList = newStockDataServiceImpl.findByCompanyCodePeriodWithNullYoy(companyCode, year, pm);
                for (NewStockData stockData : nullYoyList) {
                    List<NewStockData> previousStocks = newStockDataServiceImpl.findByCompanyCodePeriodKemu(companyCode, previousYear, pm, stockData.getKemu());
                    if (CollectionUtils.isEmpty(previousStocks)) {
                        continue;
                    }
                    NewStockData previousStock = previousStocks.get(0);

                    BigDecimal yoy = new BigDecimal("0");
                    if (previousStock.getKemuValue().compareTo (BigDecimal.ZERO) != 0) {
                        yoy = stockData.getKemuValue().subtract(previousStock.getKemuValue()).divide(
                                previousStock.getKemuValue(), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    if (yoy.compareTo(BigDecimal.ZERO) == -1) {
                        stockData.setFlag(-1);
                    }
                    stockData.setYoy(yoy);
                    newStockDataServiceImpl.save(stockData);
                }
            }
        }
    }



//
//    @GetMapping("/export")
//    public void exportFile(@RequestParam(value = "key", required = false, defaultValue = "ExcelHandler") String key,
//                           @RequestParam(value = "companyCode", required = true) String companyCode,
//                           @RequestParam(value = "monthList", required = false, defaultValue = "3, 6, 9, 12") String monthList,
//                           @RequestParam(value = "pageSize", required = false, defaultValue = "7") Long pageSize
//                           ) throws Exception {
//        if (StringUtils.isEmpty(monthList)) {
//            monthList = "3, 6, 9, 12"; // "3;6;9;12"
//        }...
//        //Download
//        byte[] bs = null;
//        try(InputStream in = handler.exportFile(exportData)) {
//            bs = IOUtils.toByteArray(in);
//        } catch (Exception e) {
//            log.error("ignore "+e.getMessage(), e);
//            throw e;
//        }
//        if (bs != null) {
//            HttpServletResponse response = ServletUtil.getResponse();
//            response.reset();
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Headers", "*");
//            response.setHeader("Content-Disposition", "attachment; filename=" +
//                    URLEncoder.encode("export-"+companyCode+"-"+ MyStringUtil.getDateTimeStr()+"."+handler.getFileType(), "UTF-8"));
//            response.addHeader("Content-Length", "" + bs.length);
//            response.setContentType("application/octet-stream; charset=UTF-8");
//            response.flushBuffer();
//            //IOUtils.copy(in, response.getOutputStream());
//            IOUtils.write(bs, response.getOutputStream());
//        }
//
//    }


}
