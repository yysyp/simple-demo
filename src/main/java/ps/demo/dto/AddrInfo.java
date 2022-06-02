package ps.demo.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddrInfo {

    private String country;
    private String area;
    private String province;
    private String city;

    private Date createdDate;
}
