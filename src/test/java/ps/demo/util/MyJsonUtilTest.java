package ps.demo.util;


import java.util.HashMap;
import java.util.Map;

public class MyJsonUtilTest {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", 123);
        map.put("bbb", "hello !!!");
        map.put("cc", new int[] {123}); //new Object will get error
        map.put("dd", null);

        String s = MyJsonUtil.simpleMap2jsonString(map);
        System.out.println(s);
        //TypeReference typeReference = new TypeReference<Map<String, Object>>() {};
        //Map<String, Object> mapResult = (Map<String, Object>) MyJsonUtil.json2Object(s, typeReference);
        System.out.println(MyJsonUtil.json2SimpleMap(s));

    }
}