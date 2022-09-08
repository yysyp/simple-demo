package pslab;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.apache.commons.lang3.StringUtils;
import ps.demo.util.MyConvertUtil;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelSortByCol {

    public static void main(String[] args) {

        String fileName = "8月应收汇总-fix-column.xls";
        File file = MyFileUtil.getFileInHomeDir(fileName);
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file);
        ExcelReader excelReader = excelReaderBuilder.build();
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        for (ReadSheet sheet : sheets) {
            System.out.println("--->>sheet ["+ (sheet.getSheetNo()+1) +"] ["+ sheet.getSheetName()+"]");
        }


        List<Object> excelLines = MyExcelUtil.readMoreThan1000RowBySheet(
                MyFileUtil.getFileInHomeDir(fileName).getPath(),
                new Sheet(1));

        excelLines.remove(0);
        //excelLines.remove(0);

        List<List<String>> table = convertToListListString(excelLines);


        List<List<String>> tableInvalid = table.stream().filter(e -> {
            String seq = e.get(2);
            return !(seq != null && seq.trim().startsWith("S"));
        }).collect(Collectors.toList());

        List<List<String>> table2 = table.stream().filter(e -> {
            String seq = e.get(2);
            return seq != null && seq.trim().startsWith("S");
        }).sorted((e1, e2) -> {
            String s1 = e1.get(2).replace("S", "").replaceFirst("-", ".");
            s1 = s1.replaceAll("-", "")
                    .replaceAll("/", "")
                    .replaceAll("&", "")
                    .replaceAll("\\\\", "");
            String s2 = e2.get(2).replace("S", "").replaceFirst("-", ".");
            s2 = s2.replaceAll("-", "")
                    .replaceAll("/", "")
                    .replaceAll("&", "")
                    .replaceAll("\\\\", "");
            return Double.compare(Double.parseDouble(s1), Double.parseDouble(s2));
        }).collect(Collectors.toList());

        //table2 remove & merge -xxx
        table2 = table2.stream().map(e -> {
            String seq = StringUtils.substringBefore(e.get(2), "-");
            e.set(2, seq);
            return e;
        }).collect(Collectors.toList());

        Map<String, List<String>> groupList =
        table2.stream().collect(Collectors.groupingBy(e -> {return e.get(2);},
                Collectors.collectingAndThen(Collectors.toList(), list -> {
                    List<String> firstLine = list.get(0);
                    if (list.size() == 1) {
                        return firstLine;
                    }

                    groupSum(list, firstLine);

                    return firstLine;
                })
                ));

        //table2.addAll(groupList.values());
        //table2 = groupList.values();

        List<List<String>> tableAll = new ArrayList<>();
        //tableAll.addAll(table2);
        tableAll.addAll(groupList.values());
        tableAll.addAll(tableInvalid);
        for (int i = 0; i < tableAll.size(); i++) {
            List<String> line = tableAll.get(i);
            line.add(0, (1000+i)+"");
        }
        //printList(tableAll);
        tableAll = tableAll.stream().sorted((e1, e2) -> {
            return Integer.compare(Integer.parseInt(e1.get(1)), Integer.parseInt(e2.get(1)));
        }).collect(Collectors.toList());

        List<List<Object>> list = MyConvertUtil.castListToObject(tableAll);
        File outExcel = MyFileUtil.getFileTsInHomeDir("sorted-excel.xlsx");
        MyExcelUtil.writeBySimple(outExcel.getPath(), list);
        System.out.println("-->OutExcel:"+outExcel);

    }

    private static void groupSum(List<List<String>> list, List<String> firstLine) {

        for (int i = 7; i <= 12; i++) {
            BigDecimal sumCol = new BigDecimal("0");
            for (List<String> line : list) {

                try {
                    String x = line.get(i);
                    if (StringUtils.isBlank(x)) {
                        x = "0";
                    }
                    BigDecimal bd = new BigDecimal(x);
                    sumCol = sumCol.add(bd);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            firstLine.set(i, sumCol.toString());
        }

    }


    public static List<List<String>> convertToListListString(List<Object> excelLines) {
        List<List<String>> excel = new ArrayList<>();
        int index = 1;
        for (Object object : excelLines) {
            List<String> line = (List<String>) object;
            line.add(0, (index++)+"");
            excel.add(line);
        }
        return excel;
    }

    public static void printList(List<List<String>> excel) {
        int i = 0;
        for (List<String> line : excel) {
            System.out.println("[line:"+(++i)+"]:"+line);
        }
    }
}
