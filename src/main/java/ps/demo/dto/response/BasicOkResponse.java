package ps.demo.dto.response;

import lombok.*;
import ps.demo.exception.CodeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BasicOkResponse implements Serializable {

    String code = CodeEnum.SUCCESS.getCode();

//    Map<String, ResLink> links;
//
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter
//    @Setter
//    @ToString
//    static class ResLink implements Serializable {
//        String href;
//        String title;
//    }

}
