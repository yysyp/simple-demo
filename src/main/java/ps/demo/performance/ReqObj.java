package ps.demo.performance;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReqObj {

    private String fileStr;

    private int num;

    private long length;

    private String resultStr;


}
