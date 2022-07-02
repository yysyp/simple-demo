package ps.demo.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HealthzResponse extends BasicOkResponse {

    private Health data;

    private String db;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Health {
        Status status;
    }

    public static enum Status {
        UP, DOWN;
    }

}
