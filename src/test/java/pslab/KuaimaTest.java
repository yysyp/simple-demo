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

        JSONObject line = new JSONObject();
        line.put("name", "firstName");
        line.put("type", "String");
        jsonArray.put(line);

        line.put("name", "lastName");
        line.put("type", "String");
        jsonArray.put(line);

        line.put("name", "age");
        line.put("type", "Integer");
        jsonArray.put(line);

        line.put("name", "score");
        line.put("type", "BigDecimal");
        jsonArray.put(line);

        line.put("name", "birthday");
        line.put("type", "Date");
        jsonArray.put(line);

        jsonObject.put("name", "Student");
        jsonObject.put("attrs", jsonArray);
        jsonArray.put(line);

        System.out.println(jsonObject.toString());
        System.out.println("Done!");
    }
}