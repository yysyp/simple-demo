package ps.demo.newstock.constant;

import java.util.HashMap;
import java.util.Map;

public class StkConstant {


    public static String debt = "debt";
    public static String benefit = "benefit";
    public static String cash = "cash";
    public static String calc = "calc";

    public static String KEMU_TIME_PATTERN = "科目.*时间";


    public static String total_assets = "total_assets";
    public static String income_main = "income_main";
    public static String cost_of_main_operation = "cost_of_main_operation";
    public static final String SALE_EXPENSE = "sale_expense";
    public static final String ADMINISTRATIVE_EXPENSE = "administrative_expense";
    public static final String RESEARCH_EXPENSE = "research_expense";
    public static final String FINANCIAL_EXPENSE = "financial_expense";
    public static final String TAX_AND_ADDITIONAL_EXPENSE = "tax_and_additional_expense";
    public static final String NET_INCOME = "net_income";
    public static final String SALE_PRODUCT_PROVIDE_SERVICE_RECEIVED_CASH = "sale_product_provide_service_received_cash";
    public static final String NET_OPERATION_GENERATED_CASH = "net_operation_generated_cash";
    public static final String ENDING_CASH_EQUIVALENT_VALUE = "ending_cash_equivalent_value";
    public static final String NET_ASSETS = "net_assets";
    public static final String TOTAL_LIABILITIES = "total_liabilities";
    public static String coreProfit = "coreProfit";
    public static String coreProfitOnRevenue = "coreProfitOnRevenue";
    public static String revenueOnAssets = "revenueOnAssets";
    public static String coreProfitOnAssets = "coreProfitOnAssets";

    public static Map<String, String> RegularKemuNameMap = new HashMap<>();

    public static Map<String, String> DebtKemuNameMap = new HashMap<>();
    public static Map<String, String> BenefitKemuNameMap = new HashMap<>();
    public static Map<String, String> CashKemuNameMap = new HashMap<>();
    public static double minMatchScore = 0.8;



    static {

        RegularKemuNameMap.put("他", "它");
        RegularKemuNameMap.put("合", "总");


        DebtKemuNameMap.put("负债总计", TOTAL_LIABILITIES);
        DebtKemuNameMap.put("所有者权益或股东权益总计", NET_ASSETS);
        //DebtKemuNameMap.put("负债和所有者权益或股东权益总计", total_assets);
        DebtKemuNameMap.put("资产总计", total_assets);

        BenefitKemuNameMap.put("营业总收入", income_main);
        BenefitKemuNameMap.put("营业成本", cost_of_main_operation);
        BenefitKemuNameMap.put("销售费用", SALE_EXPENSE);
        BenefitKemuNameMap.put("管理费用", ADMINISTRATIVE_EXPENSE);
        BenefitKemuNameMap.put("研发费用", RESEARCH_EXPENSE);
        BenefitKemuNameMap.put("财务费用", FINANCIAL_EXPENSE);
        BenefitKemuNameMap.put("税金及附加", TAX_AND_ADDITIONAL_EXPENSE);
        BenefitKemuNameMap.put("净利润", NET_INCOME);

        CashKemuNameMap.put("销售商品提供劳务收到的现金", SALE_PRODUCT_PROVIDE_SERVICE_RECEIVED_CASH);
        CashKemuNameMap.put("经营活动产生的现金流量净额", NET_OPERATION_GENERATED_CASH);
        CashKemuNameMap.put("期末现金及现金等价物余额", ENDING_CASH_EQUIVALENT_VALUE);
    }


    //others
    public static String KEMU_TIME = "科目\\时间";


}
