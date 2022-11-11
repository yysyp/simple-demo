package ps.demo.newstock.controller;


import com.alibaba.excel.util.StringUtils;
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
                        newStockDataServiceImpl.save(dto);
                    }
                }

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

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, true, "--------->>File upload failed, ex:" + e.getMessage());
        }
        return DefaultResponse.success("Upload Success");
    }


//    @PostMapping("/batchImport")
//    public BasicOkResponse batchImportFile(@RequestParam("files") MultipartFile[] files) {
//        if (files == null) {
//            throw new MyInvalidRequestException("invalid files.");
//        }
//        String msg = null;
//        for (MultipartFile multipartFile : files) {
//            msg += handleImportFile(multipartFile);
//        }
//        MyDefaultResponse response = MyDefaultResponse.success();
//        response.setMessage(msg);
//        return response;
//    }
//
////
//    @PostMapping("/import")
//    public MyDefaultResponse importFile(@RequestParam("file") MultipartFile file,
//                                        @RequestParam(value = "key", required = false, defaultValue = "ExcelHandler") String key,
//                                        @RequestParam(value = "companyCode", required = false) String companyCode,
//                                        @RequestParam(value = "reportType", required = false, defaultValue = "") String reporttype
//                                        //@RequestParam(value = "periods", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") List<Date> periods
//                                        ) {
//        String msg = handleImportFile(file, key, companyCode, reporttype);
//        MyDefaultResponse response = MyDefaultResponse.success();
//        response.setMessage(msg);
//        return response;
//    }
//
//    public String handleImportFile(MultipartFile file) {
//        return handleImportFile(file, null, null, null);
//    }
//    public String handleImportFile(MultipartFile file, String key, String companyCode, String reporttype) {
//        String fileName = file.getOriginalFilename();
//        if (StringUtils.isEmpty(key)) {
//            key = "ExcelHandler";
//            //if (fileName.toUpperCase().endsWith(".xls"))
//        }
//        if (StringUtils.isEmpty(companyCode)) {
//            Pattern pt = Pattern.compile("[0-9]{6}", Pattern.CASE_INSENSITIVE);
//            Matcher mt = pt.matcher(fileName);
//            if (mt.find()) {
//                companyCode = mt.group().trim();
//                log.info("companyCode found = {}", companyCode);
//            }
//        }
//        if (StringUtils.isEmpty(companyCode)) {
//            throw new MyInvalidRequestException("companyCode is empty, invalid companyCode.");
//        }
//        ReportType reportTypeEnum = null;
//
//        if (StringUtils.isEmpty(reporttype)) {
//            if (fileName.toUpperCase().contains("BALANCE") || fileName.toUpperCase().contains("DEBT")
//                    || fileName.toUpperCase().contains("资产")
//                    || fileName.toUpperCase().contains("负债")) {
//                reportTypeEnum = ReportType.Balancesheet;
//            } else if (fileName.toUpperCase().contains("INCOME")
//                    || fileName.toUpperCase().contains("STATEMENT")
//                    || fileName.toUpperCase().contains("BENEFIT")
//                    || fileName.toUpperCase().contains("PROFIT")
//                    || fileName.toUpperCase().contains("利润")
//                    || fileName.toUpperCase().contains("损益")
//            ) {
//                reportTypeEnum = ReportType.Incomestatement;
//                ;
//            } else if (fileName.toUpperCase().contains("CASH")
//                    || fileName.toUpperCase().contains("FLOW")
//                    || fileName.toUpperCase().contains("现金")
//                    || fileName.toUpperCase().contains("流量")) {
//                reportTypeEnum = ReportType.Cashflow;
//
//            }
//        }
//        if (reportTypeEnum == null) {
//            if (reporttype.equals("1")) {
//                reportTypeEnum = ReportType.Balancesheet;
//            } else if (reporttype.equals("2")) {
//                reportTypeEnum = ReportType.Incomestatement;
//            } else if (reporttype.equals("3")) {
//                reportTypeEnum = ReportType.Cashflow;
//            } else {
//                reportTypeEnum = ReportType.valueOf(reporttype);
//            }
//        }
//
//        log.info("File import key={}, reportType={}, fileName={}", key, reporttype, fileName);
//        FileHandler handler = SpringContextHolder.getBean(key);
//        String msg = null;
//        try (InputStream is = new BufferedInputStream(file.getInputStream())) {
//            msg = handler.importFile(is, fileName, companyCode, reportTypeEnum);
//        }catch (Exception e){
//            log.error("ignore "+e.getMessage(), e);
//            msg = e.getMessage();
//        }
//        log.info("File import success key={}, reportType={}, fileName={}", key, reporttype, fileName);
//        return msg;
//    }
//
//    @GetMapping("/export")
//    public void exportFile(@RequestParam(value = "key", required = false, defaultValue = "ExcelHandler") String key,
//                           @RequestParam(value = "companyCode", required = true) String companyCode,
//                           @RequestParam(value = "monthList", required = false, defaultValue = "3, 6, 9, 12") String monthList,
//                           @RequestParam(value = "pageSize", required = false, defaultValue = "7") Long pageSize
//                           ) throws Exception {
//        if (StringUtils.isEmpty(monthList)) {
//            monthList = "3, 6, 9, 12"; // "3;6;9;12"
//        }
//        log.info("File export key={}, monthList={}", key, monthList);
//        FileHandler handler = SpringContextHolder.getBean(key);
//        List<ReportCalculation> reportCalculations = new ArrayList<>();
//        reportCalculations.add(new MlZzGgZcCalculation());
//        reportCalculations.add(new YoyCalculation());
//        reportCalculations.add(new AverageCalculation());
//
//
//        List<List<MyCell>> allData = new ArrayList<>();
//        List<List<MyCell>> data = getBalanceReport(companyCode, pageSize, monthList);
//        if (CollectionUtils.isNotEmpty(data)) {
//            allData.addAll(new MyMatrix(data).reverseRowColumn().getTable());
//        }
//
//        data = getIncomeReport(companyCode, pageSize, monthList);
//        if (CollectionUtils.isNotEmpty(data)) {
//            allData.addAll(new MyMatrix(data).reverseRowColumn().getTable());
//        }
//
//        data = getCashReport(companyCode, pageSize, monthList);
//        if (CollectionUtils.isNotEmpty(data)) {
//            allData.addAll(new MyMatrix(data).reverseRowColumn().getTable());
//        }
//
//        //Calc yoy etc.
//        List<List<MyCell>> newAllData = allData;
//        for (ReportCalculation reportCalculation : reportCalculations) {
//            newAllData = reportCalculation.calc(newAllData);
//        }
//
//        //Set Kume and convert to List<List<Object>>
//        List<SqeItemKeysEntity> sqeItemKeysEntities = sqeItemKeysService.selectListBy(null);
//        List<List<Object>> exportData = new ArrayList<>();
//
//        for (int i = 0, n = newAllData.size(); i < n; i++) {
//            List<Object> line = new ArrayList<>(newAllData.get(i));
//            String kemuEn = ((ReportCell)line.get(0)).getKemuName();
//            if (StkConstant.KEMU_TIME.equals(kemuEn)) {
//                line.add(0, kemuEn);
//            } else {
//                Optional<SqeItemKeysEntity> optionalSqeItemKeysEntity = sqeItemKeysEntities.stream().
//                        filter(sqeItem -> sqeItem.getEnglishname().equals(kemuEn)).findAny();
//                if (optionalSqeItemKeysEntity.isPresent()) {
//                    line.add(0, optionalSqeItemKeysEntity.get().getName());
//                } else {
//                    line.add(0, kemuEn);
//                }
//            }
//            exportData.add(line);
//        }
//
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
