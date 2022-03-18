package ps.demo.dto.response;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HealthResponse implements Serializable {

    String apiVersion;
    STATUS status;

    public static enum STATUS {
        PASS("PASS"), OK ("OK"), UP("UP"),
        FAIL("FAIL"), ERROR("ERROR"), DOWN("DOWN"), WARN("WARN");
        String status;
        STATUS(String status) {
            this.status = status;
        }
        String getStatus() {
            return status;
        }
    };

    String artifactVersion;
    String timestamp;
    String instance;
    String additionalInfo;
}
