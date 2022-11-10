package ps.demo.newstock.service;

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

    public List<NewStockDataDto> handleDebtBenefitCashFlowExcel(List<Object> sheet, String companyCode, String companyName, String fileName) {
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
                newStockDataDto.setCompanyCode(companyCode);
                newStockDataDto.setCompanyName(companyName);
                newStockDataDto.setRawKemu(kemus[0]);
                newStockDataDto.setKemu(kemus[1]);
                newStockDataDto.setRawKemuValue(rawValue);
                newStockDataDto.setKemuValue(parseRawValue(rawValue));
                newStockDataDto.setPeriodYear(year);
                newStockDataDto.setPeriodMonth(month);
                newStockDataDto.setFileName(fileName);
                kemuRecords.add(newStockDataDto);
            }
        }

        return kemuRecords;
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
            rowKemuMap.put(r, new String[]{rawKemu, MyRegexUtil.regularString(rawKemu)});
        }
        return rowKemuMap;
    }


}
