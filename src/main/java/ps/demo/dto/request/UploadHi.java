package ps.demo.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UploadHi {

    private String fileStr;

    private int num;

    private long length;

}
