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
public class OperationLog implements java.io.Serializable {

    String operModul;
    String operType;
    String operDesc;
    String operMethod;
    String operRequParam;
    String operRespParam;
    String operUserId;
    String operUserName;
    String operIp;
    String operUri;
    Date operCreateTime;
    String jarVersion;

}
