package ps.demo.system.dto;

import lombok.*;
import ps.demo.common.MyBaseDto;

/**
 * Farming 2020
 *
 * @Pal
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SystemTrackingDto extends MyBaseDto {

    private String countSource;

    private String fetchSourceByPage;

    private String insertTarget;

    private String selectTargetMd5List;

    private String deleteTargetByMd5;

}



