package pslab;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyRegexUtil;
import ps.demo.util.MyStringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FinRepDebtBenefitCashParser {

    public static void main(String [] args) {

        System.out.println("Please input the xlsx file, which has debt, benefit and cash sheets:");
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine().trim();


        File file = //new File("D:\\patrick\\doc3\\3-learn\\31-invest\\fx-template-v2.xlsx");
                new File (in);
        List<Object> debt = loadExcelBySheetName(file,"debt");
        List<Object> benefit = loadExcelBySheetName(file,"benefit");
        List<Object> cash = loadExcelBySheetName(file,"cash");

        System.out.println("###Generated By FinRepDebtBenefitCashParser COPY And Paste To calc sheet Column B###");
        System.out.println("Parsing file="+in);
        //毛利率=(benefit!B11-benefit!B13)/benefit!B11
        String mll = "=(benefit!B"+findBestMatch(benefit, "营业收入")
                +"-benefit!B"+findBestMatch(benefit, "营业成本")
                +")/benefit!B"+findBestMatch(benefit, "营业收入");
        System.out.println("毛利率; 至少40%：\n"+mll);

        //ROE（净利润/净资产）=benefit!B35/debt!B78
        String jzzsyl = "=benefit!B"+findBestMatch(benefit, "净利润")
                +"/debt!B"+findBestMatch(debt, "所有者权益（或股东权益）合计");
        System.out.println("ROE（净利润/净资产）; 至少15%：\n"+jzzsyl);

        //（销售费用+管理费用+研发费用+财务费用）/ (营业总收入-营业成本）
        //=SUM(benefit!B15,benefit!B16,benefit!B17)/(benefit!B11-benefit!B13)
        String fyl = "=SUM(benefit!B"+findBestMatch(benefit, "销售费用")
                +",benefit!B"+findBestMatch(benefit, "管理费用")
                +",benefit!B"+findBestMatch(benefit, "研发费用")
                +",ABS(benefit!B"+findBestMatch(benefit, "财务费用")
                +"))/(benefit!B"+findBestMatch(benefit, "营业收入")
                +"-benefit!B"+findBestMatch(benefit, "营业成本")+")";
        System.out.println("销管研财费用/毛利(营业总收入-营业成本）; 小于70%:\n"+removeB0(fyl));

        //（销售费用）/ (营业总收入-营业成本）
        //=SUM(benefit!B15)/(benefit!B11-benefit!B13)
        String xsfyl = "=SUM(benefit!B"+findBestMatch(benefit, "销售费用")
                +")/(benefit!B"+findBestMatch(benefit, "营业收入")
                +"-benefit!B"+findBestMatch(benefit, "营业成本")+")";
        System.out.println("销售费用/毛利(营业总收入-营业成本）; 小于30%:\n"+removeB0(xsfyl));

        //经营现金流净额 / 净利润==cash!B5/benefit!B35
        String xjb = "=cash!B"+findBestMatch(cash, "经营活动产生的现金流量净额")+"/benefit!B"
                +findBestMatch(benefit, "净利润");
        System.out.println("经营现金流净额 / 净利润; 大于等于1\n" + removeB0(xjb));

        //销售商品、提供劳务收到的现金 / 营业收入=cash!B11/benefit!B11
        String xsysb = "=cash!B"+findBestMatch(cash, "销售商品、提供劳务收到的现金")
                +"/benefit!B"+findBestMatch(benefit, "营业收入");
        System.out.println("销售商品、提供劳务收到的现金 / 营业收入; 大于等于1 \n" + removeB0(xsysb));

        //（土地使用权+投资性房地产+固定资产+在建工程+生产性生物资产 +油气资产）/总资产
        String heavyRate = "=SUM(debt!B"+findBestMatch(debt, "投资性房地产")
                +",debt!B" +findBestMatch(debt, "固定资产")
                +",debt!B" +findBestMatch(debt, "在建工程合计")
                +",debt!B" +findBestMatch(debt, "生产性生物资产")
                +",debt!B" +findBestMatch(debt, "油气资产")
                +")/debt!B"+findBestMatch(debt, "负债和所有者权益（或股东权益）合计");
        System.out.println("（土地使用权+投资性房地产+固定资产+在建工程+生产性生物资产 +油气资产）/总资产; 比如小于12% \n" + removeB0(heavyRate));

        //（xxx应收xxx - 应收票据）/总资产；
        String ysbl = "=SUM(debt!B"+findBestMatch(debt, "应收账款")
                +",debt!B"+findBestMatch(debt, "其他应收款")
                +")/debt!B"
                +findBestMatch(debt, "负债和所有者权益（或股东权益）合计");
        System.out.println("（xxx应收xxx - 应收票据）/总资产；小于 30% \n" + removeB0(ysbl));

        //（xxx金融资产+xxx投资xxx+持有待售资产）/ 总资产; 小于 10%
        String jrczl = "=SUM(debt!B"+findBestMatch(debt, "交易性金融资产")
                +",debt!B"+findBestMatch(debt, "可供出售金融资产")
                +",debt!B"+findBestMatch(debt, "其他非流动金融资产")
                +",debt!B"+findBestMatch(debt, "长期股权投资")
                +",debt!B"+findBestMatch(debt, "其他权益工具投资")
                +",debt!B"+findBestMatch(debt, "持有至到期投资")
                +",debt!B"+findBestMatch(debt, "投资性房地产")
                +")/debt!B"+findBestMatch(debt, "负债和所有者权益（或股东权益）合计");

        System.out.println("（xxx金融资产+xxx投资xxx+持有待售资产）/ 总资产; 小于 10%  \n" + removeB0(jrczl));

        //期末现金及现金等价物余额/有息负债
        String yxfz = "=cash!B"+findBestMatch(cash, "期末现金及现金等价物余额")
                +"/SUM(debt!B"+findBestMatch(debt, "短期借款")
                +",debt!B"+findBestMatch(debt, "长期借款")
                +",debt!B"+findBestMatch(debt, "一年内到期的非流动负债")
                +",debt!B"+findBestMatch(debt, "一年内到期的融资租赁负债")
                +",debt!B"+findBestMatch(debt, "长期融资租赁负债")
                +",debt!B"+findBestMatch(debt, "应付债券")+")";
        System.out.println("期末现金及现金等价物余额/有息负债；大于1 \n" + removeB0(yxfz));

    }

    public static String removeB0(String formula) {
        return formula.replaceAll("debt!B0", "0")
                .replaceAll("benefit!B0", "0")
                .replaceAll("cash!B0", "0");
    }
    //Index Offset: 2
    public static int findBestMatch(List<Object> table, String itemName) {
        double score = 0.5d;
        int row = 0;
        for (int i = 0, n = table.size(); i < n; i++) {
            List<String> line = (List<String>) table.get(i);
            double r = MyStringUtil.getLcsOrMixContainsRatio(itemName, line.get(0));
            if (r > score) {
                score = r;
                row = i + 2;
            }
        }
        return row;
    }

    public static void printTable(List<Object> table) {
        for (int i = 0, n = table.size(); i < n; i++) {
            List<String> line = (List<String>) table.get(i);
            System.out.println("line<"+i+">: "+line);
        }
    }

    public static List<Object> loadExcelBySheetName(File file, String sheetName) {
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file);
        ExcelReader excelReader = excelReaderBuilder.build();
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        int sheetNo = -1;
        StringBuilder buffer = new StringBuilder();
        for (ReadSheet sheet : sheets) {
            buffer.append(sheet.getSheetName());
            buffer.append(", ");
            if (sheet.getSheetName().equals(sheetName)) {
                sheetNo = sheet.getSheetNo() + 1;
                //break;
            }
        }
        if (sheetNo < 0) {
            throw new RuntimeException("Not find the sheet "+sheetName);
        }
        List<Object> excelLines = MyExcelUtil.readMoreThan1000RowBySheet(
                file.getPath(),
                new Sheet(sheetNo));
        return excelLines;
    }

}
