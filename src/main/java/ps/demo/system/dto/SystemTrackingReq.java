package ps.demo.system.dto;

import lombok.*;

import ps.demo.common.MyPageReq;


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
public class SystemTrackingReq extends MyPageReq {

    private String key;

    private String countSource;

    private String fetchSourceByPage;

    private String insertTarget;

    private String selectTargetMd5List;

    private String deleteTargetByMd5;

}



