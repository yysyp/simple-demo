package ps.demo.dto.response;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse implements Serializable {

    String code;
    String message;
    String trace;
    String correlationId;
    String instance;
    String timestamp;
    String path;

}
