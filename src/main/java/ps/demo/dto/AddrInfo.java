package ps.demo.dto;

import lombok.*;

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

}
