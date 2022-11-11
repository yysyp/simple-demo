package ps.demo.newstock.constant;

import java.util.HashMap;
import java.util.Map;

public class StkConstant {


    public static String debt = "debt";
    public static String benefit = "benefit";
    public static String cash = "cash";

    public static String KEMU_TIME_PATTERN = "科目.*时间";


    public static String total_assets = "total_assets";
    public static String income_main = "income_main";

    public static Map<String, String> RegularKemuNameMap = new HashMap<>();

    public static Map<String, String> DebtKemuNameMap = new HashMap<>();
    public static Map<String, String> BenefitKemuNameMap = new HashMap<>();
    public static Map<String, String> CashKemuNameMap = new HashMap<>();
    public static double minMatchScore = 0.8;
    static {

        RegularKemuNameMap.put("他", "它");
        RegularKemuNameMap.put("合", "总");


        DebtKemuNameMap.put("负债总计", "total_liabilities");
        DebtKemuNameMap.put("所有者权益或股东权益总计", "net_assets");
        //DebtKemuNameMap.put("负债和所有者权益或股东权益总计", total_assets);
        DebtKemuNameMap.put("资产总计", total_assets);

        BenefitKemuNameMap.put("营业总收入", income_main);
        BenefitKemuNameMap.put("营业成本", "cost_of_main_operation");
        BenefitKemuNameMap.put("销售费用", "sale_expense");
        BenefitKemuNameMap.put("管理费用", "administrative_expense");
        BenefitKemuNameMap.put("研发费用", "research_expense");
        BenefitKemuNameMap.put("财务费用", "financial_expense");
        BenefitKemuNameMap.put("税金及附加", "tax_and_additional_expense");
        BenefitKemuNameMap.put("净利润", "net_income");

        CashKemuNameMap.put("销售商品提供劳务收到的现金", "sale_product_provide_service_received_cash");
        CashKemuNameMap.put("经营活动产生的现金流量净额", "net_operation_generated_cash");
        CashKemuNameMap.put("期末现金及现金等价物余额", "ending_cash_equivalent_value");
    }


    //others
    public static String KEMU_TIME = "科目\\时间";


}
