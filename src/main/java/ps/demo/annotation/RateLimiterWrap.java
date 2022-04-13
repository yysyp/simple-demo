package ps.demo.annotation;

import com.google.common.util.concurrent.RateLimiter;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateLimiterWrap {

    private RateLimiter rateLimiter;

    private long timeoutMs;

    private String message;
}
