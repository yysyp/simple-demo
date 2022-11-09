package ps.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.exception.CodeEnum;

@Getter
@Setter
@ToString
public class DefaultResponse extends AbsResponse {

    //extends to add data properties

    // If error then throw either:
    // a) new BadRequestException(CodeEnum.BAD_REQUEST);
    // b) new ServerApiException(CodeEnum.DUPLICATED_KEY);

}
