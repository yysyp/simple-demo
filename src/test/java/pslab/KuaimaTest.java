package pslab;

import com.fasterxml.jackson.databind.JsonNode;
import ps.demo.util.MyThymeleafUtil;
import ps.demo.util.json.JSONArray;
import ps.demo.util.json.JSONException;
import ps.demo.util.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class KuaimaTest {

    public static void main(String[] args) throws JSONException {
        String content = "[# th:with=\"cur_dir=., out_dir='x/'\"]" +
                " cur_dir+out_dir=[( ${cur_dir} + ${out_dir} +'Controller')]" +
                " [(${#strings.replace(name, '.', '/')})]" +
                " [/]";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Patrick.hah.SONG");
        System.out.println(MyThymeleafUtil.processText(content, params));

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        jsonArray.put(buildLine("firstName", "String"));
        jsonArray.put(buildLine("lastName", "String"));
        jsonArray.put(buildLine("age", "Integer"));
        jsonArray.put(buildLine("score", "BigDecimal"));
        jsonArray.put(buildLine("birthday", "Date"));

        jsonObject.put("name", "Student");
        jsonObject.put("attrs", jsonArray);

        System.out.println(jsonObject.toString());
        System.out.println("Done!");
    }

    public static JSONObject buildLine(String name, String type) throws JSONException {
        JSONObject line = new JSONObject();
        line.put("name", name);
        line.put("type", type);
        return line;
    }
}