package ps.demo.newstock.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import ps.demo.newstock.constant.StkConstant;
import ps.demo.newstock.dto.NewStockDataDto;
import ps.demo.util.MyRegexUtil;
import ps.demo.util.MyStringUtil;
import ps.demo.util.matrix.MyMatrix;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HandleUpload {

    public static boolean isKemuPeriodCell(String str) {
        return Pattern.matches(StkConstant.KEMU_TIME_PATTERN, str.trim());
    }

    public static BigDecimal parseRawValue(String rawValue) {
        String number = MyRegexUtil.removeSymbolsByReg(rawValue, "[^(0-9\\-\\+\\.)]");
        try {
            return new BigDecimal(number);
        } catch (Exception e) {
        }
        return new BigDecimal("0");
    }

    public static String rawKemuNameToKemuName(String rowKemuName) {
        String s = MyRegexUtil.regularString(rowKemuName);
        for (String key : StkConstant.RegularKemuNameMap.keySet()) {
            String value = StkConstant.RegularKemuNameMap.get(key);
            s = s.replaceAll(key, value);
        }
        return s;
    }

    public static Date tryStrToDate(String dateStr, String... fmts) {
        if (fmts == null || fmts.length == 0) {
            fmts = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "yyyy", "HH:mm:ss", "HH:mm"};
        }
        for (String fmt : fmts) {
            try {
                return new SimpleDateFormat(fmt).parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public List<NewStockDataDto> handleDebtBenefitCashFlowExcel(List<Object> sheet, String kemuType, String companyCode, String companyName, String fileName) {
        if (sheet == null) {
            return new ArrayList<>();
        }
        /*
         * 报表结构如下：
         * 科目\时间    2022-12-31  2022-09-30  ...
         * 现金..
         * 应收..
         * ...
         */
        //(1) Locate kemuAndDate and construct rowKemuMap and columnPeriodDateMap
        int kemuAndDateRow = 0;
        int kemuAndDateCol = 0;
        boolean kemuAndDateCellFound = false;
        Map<Integer, String[]> rowKemuMap = new HashMap<>();
        Map<Integer, Date> columnPeriodDateMap = new HashMap<>();
        for (int r = 0, rn = sheet.size(); r < rn; r++) {
            if (kemuAndDateCellFound) {
                break;
            }
            List<String> line = (List<String>) sheet.get(r);
            for (int c = 0, cn = line.size(); c < cn; c++) {
                String cellValue = line.get(c);
                if (isKemuPeriodCell(cellValue)) {
                    kemuAndDateRow = r;
                    kemuAndDateCol = c;
                    columnPeriodDateMap = constructColumnPeriodDateMap(line, c);
                    rowKemuMap = constructRowKemuMap(sheet, r, c);
                    kemuAndDateCellFound = true;
                    break;
                }
            }
        }

        //(2) processing data, cells to Dto list.
        List<NewStockDataDto> kemuRecords = new ArrayList<>();
        for (int r = kemuAndDateRow + 1, rn = sheet.size(); r < rn; r++) {
            List<String> line = (List<String>) sheet.get(r);
            for (int c = kemuAndDateCol + 1, cn = line.size(); c < cn; c++) {
                Date period = columnPeriodDateMap.get(c);
                String[] kemus = rowKemuMap.get(r);
                if (period == null || kemus == null) {
                    continue;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(period);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                if (month == 1) {
                    month = 12;
                }
                String rawValue = line.get(c);
                if (rawValue == null) {
                    continue;
                }

                NewStockDataDto newStockDataDto = new NewStockDataDto();
                final int yearfinal = year;
                final int monthfinal = month;
                Optional exist = kemuRecords.stream().filter(e -> e.getPeriodYear().equals(yearfinal)
                && e.getPeriodMonth().equals(monthfinal)
                && e.getCompanyCode().equals(companyCode)
                && e.getKemu().equals(kemus[1])).findAny();
                if (exist.isPresent()) {
                    continue;
                }
                newStockDataDto.setKemuType(kemuType);
                newStockDataDto.setCompanyCode(companyCode);
                newStockDataDto.setCompanyName(companyName);
                newStockDataDto.setRawKemu(kemus[0]);
                newStockDataDto.setKemu(kemus[1]);
                newStockDataDto.setRawKemuValue(rawValue);
                newStockDataDto.setKemuValue(parseRawValue(rawValue));
                newStockDataDto.setPeriodYear(year);
                newStockDataDto.setPeriodMonth(month);
                newStockDataDto.setFileName(fileName);
                findAndSetKemuEnglishName(newStockDataDto);
                kemuRecords.add(newStockDataDto);
            }
        }

        return kemuRecords;
    }

    public void calcPctInAssetOrRevenue(List<NewStockDataDto> kemuRecords) {
        if (kemuRecords == null) {
            return;
        }
        Optional<NewStockDataDto> assetKemu = null;
        Optional<NewStockDataDto> revenueKemu = null;

        List<NewStockDataDto> assetList = kemuRecords.stream().filter(e ->
                StkConstant.total_assets.equals(e.getKemuEn())).collect(Collectors.toList());

        List<NewStockDataDto> revenueList = kemuRecords.stream().filter(e ->
                StkConstant.income_main.equals(e.getKemuEn())).collect(Collectors.toList());

        for (NewStockDataDto newStockDataDto : kemuRecords) {
            String kemuType = newStockDataDto.getKemuType();
            Integer year = newStockDataDto.getPeriodYear();
            Integer month = newStockDataDto.getPeriodMonth();
            if (StkConstant.debt.equals(kemuType)) {
                assetKemu = assetList.stream().filter(e -> e.getPeriodYear().equals(year) && e.getPeriodMonth().equals(month)).findAny();
                if (!assetKemu.isPresent()) {
                    continue;
                }
                BigDecimal pctOnXx = newStockDataDto.getKemuValue().divide(assetKemu.get().getKemuValue(), BigDecimal.ROUND_HALF_UP, 4);
                newStockDataDto.setPctInAssetOrRevenue(pctOnXx);
            } else if (StkConstant.benefit.equals(kemuType)) {
                revenueKemu = revenueList.stream().filter(e -> e.getPeriodYear().equals(year) && e.getPeriodMonth().equals(month)).findAny();
                if (!revenueKemu.isPresent()) {
                    continue;
                }
                BigDecimal pctOnXx = newStockDataDto.getKemuValue().divide(revenueKemu.get().getKemuValue(), BigDecimal.ROUND_HALF_UP, 4);
                newStockDataDto.setPctInAssetOrRevenue(pctOnXx);
            } else if (StkConstant.cash.equals(kemuType)) {
            }
        }

    }

    public static void findAndSetKemuEnglishName(NewStockDataDto newStockDataDto) {
        String kemuType = newStockDataDto.getKemuType();
        Map<String, String> map = null;
        if (StkConstant.debt.equals(kemuType)) {
            map = StkConstant.DebtKemuNameMap;
        } else if (StkConstant.benefit.equals(kemuType)) {
            map = StkConstant.BenefitKemuNameMap;
        } else if (StkConstant.cash.equals(kemuType)) {
            map = StkConstant.CashKemuNameMap;
        }
        if (map != null) {
            Iterator<String> iterator = map.keySet().iterator();
            String kemuEn = matchInMap(map, newStockDataDto.getKemu(), StkConstant.minMatchScore);
            if (kemuEn != null) {
                newStockDataDto.setKemuEn(kemuEn);
            }
        }
    }

    public static String matchInMap(Map<String, String> map, String q, double minMatchScore) {
        double score = 0d;
        String foundValue = null;
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            double s = MyStringUtil.getLcsOrMixContainsRatio(q, key);
            if (s > score) {
                score = s;
                foundValue = map.get(key);
            }
        }
        if (score < minMatchScore) {
            return null;
        }
        return foundValue;
    }

    public static Map<Integer, Date> constructColumnPeriodDateMap(List<String> line, int kemuAndDateCol) {
        Map<Integer, Date> columnPeriodDateMap = new HashMap<>();
        for (int i = kemuAndDateCol + 1; i < line.size(); i++) {
            String period = line.get(i);
            Date p = tryStrToDate(period);
            columnPeriodDateMap.put(i, p);
        }
        return columnPeriodDateMap;
    }

    public static Map<Integer, String[]> constructRowKemuMap(List<Object> sheet, int kemuAndDateRow, int kemuAndDateCol) {
        Map<Integer, String[]> rowKemuMap = new HashMap<>();
        for (int r = kemuAndDateRow + 1, rn = sheet.size(); r < rn; r++) {
            List<String> line = (List<String>) sheet.get(r);

            if (line.size() <= kemuAndDateCol) {
                continue;
            }
            String rawKemu = line.get(kemuAndDateCol);

            rowKemuMap.put(r, new String[]{rawKemu, rawKemuNameToKemuName(rawKemu)});
        }
        return rowKemuMap;
    }


}
