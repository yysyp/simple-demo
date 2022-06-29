package pslab;

import com.alibaba.excel.metadata.Sheet;
import ps.demo.util.MyConvertUtil;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelSortByCol {

    public static void main(String[] args) {

        List<Object> excelLines = MyExcelUtil.readMoreThan1000RowBySheet(
                MyFileUtil.getFileInHomeDir("2022销售记录-共享版--V2-sort.xlsx").getPath(),
                new Sheet(2));

        excelLines.remove(0);
        excelLines.remove(0);

        List<List<String>> table = convertToListListString(excelLines);


        List<List<String>> tableInvalid = table.stream().filter(e -> {
            String seq = e.get(1);
            return !(seq != null && seq.trim().startsWith("S"));
        }).collect(Collectors.toList());

        List<List<String>> table2 = table.stream().filter(e -> {
            String seq = e.get(1);
            return seq != null && seq.trim().startsWith("S");
        }).sorted((e1, e2) -> {
            String s1 = e1.get(1).replace("S", "").replaceFirst("-", ".");
            s1 = s1.replaceAll("-", "")
                    .replaceAll("/", "")
                    .replaceAll("&", "")
                    .replaceAll("\\\\", "");
            String s2 = e2.get(1).replace("S", "").replaceFirst("-", ".");
            s2 = s2.replaceAll("-", "")
                    .replaceAll("/", "")
                    .replaceAll("&", "")
                    .replaceAll("\\\\", "");
            return Double.compare(Double.parseDouble(s1), Double.parseDouble(s2));
        }).collect(Collectors.toList());

        List<List<String>> tableAll = new ArrayList<>();
        tableAll.addAll(table2);
        tableAll.addAll(tableInvalid);
        for (int i = 0; i < tableAll.size(); i++) {
            List<String> line = tableAll.get(i);
            line.add(0, (1000+i)+"");
        }
        printList(tableAll);
        tableAll = tableAll.stream().sorted((e1, e2) -> {
            return Integer.compare(Integer.parseInt(e1.get(1)), Integer.parseInt(e2.get(1)));
        }).collect(Collectors.toList());

        List<List<Object>> list = MyConvertUtil.castListToObject(tableAll);
        File outExcel = MyFileUtil.getFileTsInHomeDir("sorted-excel.xlsx");
        MyExcelUtil.writeBySimple(outExcel.getPath(), list);

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
