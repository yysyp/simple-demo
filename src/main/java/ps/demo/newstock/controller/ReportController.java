package ps.demo.newstock.controller;


import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ps.demo.dto.response.DefaultResponse;
import ps.demo.dto.response.StockReportResponse;
import ps.demo.newstock.constant.StkConstant;
import ps.demo.newstock.entity.NewStockData;
import ps.demo.newstock.service.HandleUpload;
import ps.demo.newstock.service.NewStockDataServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/newstock/report")
public class ReportController {

    @Autowired
    private HandleUpload handleUpload;

    @Autowired
    private NewStockDataServiceImpl newStockDataServiceImpl;


    //
    @GetMapping("/json")
    public StockReportResponse exportJson(@RequestParam(value = "companyCode", required = true) String companyCode,
                                          @RequestParam(value = "fromYear", required = true, defaultValue = "2000") Integer fromYear,
                                          @RequestParam(value = "toYear", required = false, defaultValue = "0") Integer toYear,
                                          @RequestParam(value = "months", required = true, defaultValue = "3,6,9,12") List<Integer> months
    ) throws Exception {
        StockReportResponse stockReportResponse = new StockReportResponse();
        List<List<Object>> calcLines = new ArrayList<>();
        List<List<Object>> debtLines = new ArrayList<>();
        List<List<Object>> benefitLines = new ArrayList<>();
        List<List<Object>> cashLines = new ArrayList<>();

        List<String> calcTableColumn0s = new ArrayList<>();
        List<String> debtTableColumn0s = new ArrayList<>();
        List<String> benefitTableColumn0s = new ArrayList<>();
        List<String> cashTableColumn0s = new ArrayList<>();

        List<Map<String, String>> columnsTitleMaps = new ArrayList<>();
        int columnCount = 0;
        Map<String, String> columnTitleMap = new TreeMap<>();
        columnsTitleMaps.add(columnTitleMap);
        columnTitleMap.put("Header", "Index");
        columnTitleMap.put("accessor", "c"+columnCount++);
        boolean kemuAddedFlag = false;

        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        if (toYear == 0) {
            toYear = nowYear;
        }
        List<Integer> sortedMonths = months.stream().sorted().collect(Collectors.toList());
        for (int year = fromYear; year <= toYear; year++) {
            for (int mi = 0; mi < sortedMonths.size(); mi++) {
                int month = sortedMonths.get(mi);
                List<NewStockData> newStockDataList = newStockDataServiceImpl.findByCompanyCodePeriod(companyCode, year, month);
                if (CollectionUtils.isEmpty(newStockDataList)) {
                    continue;
                }

                if (!kemuAddedFlag) {
                    kemuAddedFlag = true;
                    columnTitleMap = new TreeMap<>();
                    columnsTitleMaps.add(columnTitleMap);
                    columnTitleMap.put("Header", "[####Kemu####]");
                    columnTitleMap.put("accessor", "c"+columnCount++);
                }

                NewStockData d = newStockDataList.get(0);
                String yearMonth = d.getPeriodYear()+"-"+d.getPeriodMonth();
                columnTitleMap = new TreeMap<>();
                columnsTitleMaps.add(columnTitleMap);
                columnTitleMap.put("Header", yearMonth);
                columnTitleMap.put("accessor", "c"+columnCount++);
                columnTitleMap = new TreeMap<>();
                columnsTitleMaps.add(columnTitleMap);
                columnTitleMap.put("Header", "YOY");
                columnTitleMap.put("accessor", "c"+columnCount++);
                columnTitleMap = new TreeMap<>();
                columnsTitleMaps.add(columnTitleMap);
                columnTitleMap.put("Header", "PctInATOrR");
                columnTitleMap.put("accessor", "c"+columnCount++);
                columnTitleMap = new TreeMap<>();
                columnsTitleMaps.add(columnTitleMap);
                columnTitleMap.put("Header", "DeltaPctToX");
                columnTitleMap.put("accessor", "c"+columnCount++);

                if (calcTableColumn0s.isEmpty()) {
                    calcTableColumn0s = getLineHeaders(newStockDataList, StkConstant.calc);
                }
                fillOtherColumnsByMatchColumn0(newStockDataList, calcLines, calcTableColumn0s);

                if (debtTableColumn0s.isEmpty()) {
                    debtTableColumn0s = getLineHeaders(newStockDataList, StkConstant.debt);
                }
                fillOtherColumnsByMatchColumn0(newStockDataList, debtLines, debtTableColumn0s);

                if (benefitTableColumn0s.isEmpty()) {
                    benefitTableColumn0s = getLineHeaders(newStockDataList, StkConstant.benefit);
                }
                fillOtherColumnsByMatchColumn0(newStockDataList, benefitLines, benefitTableColumn0s);

                if (cashTableColumn0s.isEmpty()) {
                    cashTableColumn0s = getLineHeaders(newStockDataList, StkConstant.cash);
                }
                fillOtherColumnsByMatchColumn0(newStockDataList, cashLines, cashTableColumn0s);
            }
        }

//        //calc
        StockReportResponse.CdbcReport cdbcReport = new StockReportResponse.CdbcReport();
        cdbcReport.setReportType(StkConstant.calc);
        cdbcReport.setTableData(convertToListMapTableData(calcLines));
        cdbcReport.setColumns(columnsTitleMaps);
        stockReportResponse.getData().add(cdbcReport);

        //debt
        cdbcReport = new StockReportResponse.CdbcReport();
        cdbcReport.setReportType(StkConstant.debt);
        cdbcReport.setTableData(convertToListMapTableData(debtLines));
        cdbcReport.setColumns(columnsTitleMaps);
        stockReportResponse.getData().add(cdbcReport);

        //benefit
        cdbcReport = new StockReportResponse.CdbcReport();
        cdbcReport.setReportType(StkConstant.benefit);
        cdbcReport.setTableData(convertToListMapTableData(benefitLines));
        cdbcReport.setColumns(columnsTitleMaps);
        stockReportResponse.getData().add(cdbcReport);

        //cash
        cdbcReport = new StockReportResponse.CdbcReport();
        cdbcReport.setReportType(StkConstant.cash);
        cdbcReport.setTableData(convertToListMapTableData(cashLines));
        cdbcReport.setColumns(columnsTitleMaps);
        stockReportResponse.getData().add(cdbcReport);

        return stockReportResponse;
    }

    private List<Map<String, Object>> convertToListMapTableData(List<List<Object>> listList) {
        List<Map<String, Object>> table = new ArrayList<>();
        int lineCount = 0;
        for (List<Object> line : listList) {
            Map<String, Object> lineMap = new TreeMap<>();
            table.add(lineMap);
            lineMap.put("c0", lineCount++);
            for (int c = 0; c < line.size(); c++) {
                lineMap.put("c"+(c+1), line.get(c));
            }
        }
        return table;
    }


    /**
     * Get first Column rows
     */
    private List<String> getLineHeaders(List<NewStockData> newStockDataList, String kemuType) {
        List<String> lineHeaders = new ArrayList<>();
        if (CollectionUtils.isEmpty(newStockDataList)) {
            return lineHeaders;
        }
        List<NewStockData> newStockDatas = newStockDataList.stream().filter(e -> e.getKemuType().equals(kemuType))
                .collect(Collectors.toList());
        //lineHeaders.add(StkConstant.KEMU_TIME);
        for (NewStockData newStockData : newStockDatas) {
            lineHeaders.add(newStockData.getRawKemu());
        }
        return lineHeaders;
    }

    private void fillOtherColumnsByMatchColumn0(List<NewStockData> newStockDataList, List<List<Object>> resultTable, List<String> column0s) {
        if (resultTable.isEmpty()) {
            for (String rawKemu : column0s) {
                List<Object> line = new ArrayList<>();
                line.add(rawKemu);
                resultTable.add(line);
            }
        }
        for (List line : resultTable) {
            String rawKemu = line.get(0)+"";
            if (StringUtils.isEmpty(rawKemu)) {
                continue;
            }
            if (rawKemu.equals(StkConstant.KEMU_TIME)) {
                NewStockData d = newStockDataList.get(0);
                String yearMonth = d.getPeriodYear()+"-"+d.getPeriodMonth();
                line.add(yearMonth);
                line.add("YOY");
                line.add("PercentInAssetOrRevenue");
                line.add("CoreProfitOnAssetEffectPercentByYoyDelta");
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


    @PostMapping("/delete")
    public DefaultResponse deleteByCompanyCode(@RequestParam(value = "companyCode", required = true) String companyCode) throws Exception {
        newStockDataServiceImpl.deleteByCompanyCode(companyCode.trim());
        return DefaultResponse.success("ok");
    }


}
