package ps.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import ps.demo.exception.CodeEnum;

import java.io.Serializable;

@Getter
@Setter
public class AbsResponse implements Serializable {

    protected String code = CodeEnum.SUCCESS.getCode();
    protected String message;
    protected String trace;
    protected String correlationId;
    protected String instance;
    protected String timestamp;
    protected String path;

}
