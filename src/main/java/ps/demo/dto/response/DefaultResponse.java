package ps.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.exception.CodeEnum;

@Getter
@Setter
@ToString
public class DefaultResponse extends AbsResponse {

    //1: no data
    public static DefaultResponse success(String message) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setMessage(message);
        return defaultResponse;
    }

    //2: extends to add data properties


    //3:
    // If error then throw either:
    // a) new BadRequestException(CodeEnum.BAD_REQUEST);
    // b) new ServerApiException(CodeEnum.DUPLICATED_KEY);

}
