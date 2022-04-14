package ps.demo.annotation;

import lombok.*;

import java.util.Date;

/**
 * @author yunpeng.song
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExceptionLog {

    String ExcRequParam;

    String OperMethod;

    String ExcName;

    String ExcMessage;

    String OperUri;

    String OperIp;

    Date OperCreateTime;


}
