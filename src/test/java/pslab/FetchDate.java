package pslab;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.codehaus.plexus.util.StringUtils;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyRegexUtil;
import ps.demo.util.MyStringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FetchDate {

    public static List<Object> combineList(List<Object> sheetList, List<String> li) {
        List<Object> result = new ArrayList<>();
        int liSize = li.size();
        for (int i = 0, n = sheetList.size(); i < n; i++) {
            List<String> oneline = new ArrayList<>();
            result.add(oneline);
            List<String> line = (List<String>) sheetList.get(i);
            if (i < liSize) {
                oneline.add(li.get(i));
            } else {
                oneline.add("");
            }
            oneline.addAll(line);
        }
        return result;
    }

//    public static List<List<Object>> castSheetForOutputSheet(List<Object> lists) {
//        List<List<Object>> listObj = new ArrayList<>();
//        for (Object line : lists) {
//            listObj.add((List<Object>) line);
//        }
//        return listObj;
//    }

    public static void main(String[] args) {

        List<Object> list1 = MyExcelUtil.loadExcelBySheetNameInHomeDir("20220401-20220731到货单-1.xlsx",
                "Sheet");
        List<Object> list2 = MyExcelUtil.loadExcelBySheetNameInHomeDir("20220401-20220731销货单-2.xlsx",
                "Sheet");
        List<Object> list3 = MyExcelUtil.loadExcelBySheetNameInHomeDir("2022中山销货表.xls",
                "中山销货");

//        MyExcelUtil.printList(list1);
//        MyExcelUtil.printList(list2);\

        List<String> li1 = new ArrayList<>();
        List<String> li2 = new ArrayList<>();
        for (Object object : list3) {
            List<String> line = (List<String>) object;
            String sn = line.get(0);
            li1.add(matchFetchSheet1(list1, sn));
            li2.add(matchFetchSheet1(list2, sn));
        }

        List<Object> result = combineList(combineList(list3, li1), li2);

        File tofile = MyExcelUtil.saveSheetDataToExcelInHomeDir("mergedComparingDate.xlsx", result);
        
        System.out.println("-->OutExcel:" + tofile);

    }

    public static String matchFetchSheet1(List<Object> sheet1, String sn) {
        if (StringUtils.isBlank(sn)) {
            return "";
        }
        String parsedSn = MyRegexUtil.removeSymbolsExceptAlpNumberHyphenUnderline(sn);
        if (StringUtils.isBlank(parsedSn)) {
            return "";
        }
        for (Object object : sheet1) {
            List<String> line = (List<String>) object;
            String s = MyRegexUtil.removeSymbolsExceptAlpNumberHyphenUnderline(line.get(0));
            if (StringUtils.isBlank(s)) {
                continue;
            }
            if (parsedSn.equals(s)) {
                return line.get(2);
            }
        }
        return "";
    }
}
