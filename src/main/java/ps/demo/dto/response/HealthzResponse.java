package ps.demo.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HealthzResponse extends BasicOkResponse {

    Data data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Data {
        Status status;
    }

    public static enum Status {
        UP, DOWN;
    }

}
