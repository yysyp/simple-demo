

package ps.demo.qn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.math.*;

import org.apache.commons.lang3.StringUtils;
import ps.demo.common.MyBaseDto;
import lombok.*;
import ps.demo.util.MyJsonUtil;

import java.util.*;
import java.math.*;
@Getter
@Setter
@ToString
public class QuestionnaireResultDto extends MyBaseDto {

    private String uri;
    private String name;
    private String htmlFile;
    private String responseData;

    public List<String> toKeyList() {
        List<String> keyList = new ArrayList<>();
        keyList.add("uri");
        keyList.add("name");
        keyList.add("createdOn");
        Map<String, Object> map = MyJsonUtil.json2SimpleMap(responseData);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            keyList.add(key);
        }
        return keyList;
    }

    public List<Object> toList() {
        List<Object> line = new ArrayList<>();
        line.add(uri);
        line.add(name);
        line.add(this.getCreatedOn());
        Map<String, Object> map = MyJsonUtil.json2SimpleMap(responseData);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue()+"";
            value = StringUtils.removeEnd(StringUtils.removeStart(value, "["), "]");
            line.add(value);
        }
        return line;
    }

}


