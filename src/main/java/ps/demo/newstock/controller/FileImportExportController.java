package ps.demo.newstock.controller;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/newstock/file")
public class FileImportExportController {


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
