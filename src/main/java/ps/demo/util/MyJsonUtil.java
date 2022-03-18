package ps.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc
 */
@Slf4j
public class MyJsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //Set to ignore exists in json but not in java properties
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String object2Json(Object o) {
        if (o == null) {
            return null;
        }

        if (o instanceof String) {
            return o.toString();
        }

        String s = null;

        try {
            s = mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.info("--->> error object2Json err={}", e.getMessage());
        }
        return s;
    }

    public static <T> List<String> listObject2ListJson(List<T> objects) {
        if (objects == null) {
            return null;
        }

        List<String> lists = new ArrayList<String>();
        for (T t : objects) {
            lists.add(MyJsonUtil.object2Json(t));
        }

        return lists;
    }

    public static <T> List<T> listJson2ListObject(List<String> jsons, Class<T> c) {
        if (jsons == null) {
            return null;
        }

        List<T> ts = new ArrayList<T>();
        for (String j : jsons) {
            ts.add(MyJsonUtil.json2Object(j, c));
        }

        return ts;
    }

    public static <T> T json2Object(String json, Class<T> c) {
        if (StringUtils.hasLength(json) == false) {
            return null;
        }

        T t = null;
        try {
            t = mapper.readValue(json, c);
        } catch (Exception e) {
            log.info("--->> error json2Object err={}", e.getMessage());
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public static <T> T json2Object(String json, TypeReference<T> tr) {
        if (StringUtils.hasLength(json) == false) {
            return null;
        }

        T t = null;
        try {
            t = (T) mapper.readValue(json, tr);
        } catch (Exception e) {
            log.info("--->> error json2Object TypeReference err={}", e.getMessage());
        }
        return (T) t;
    }
}
