package ps.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {

    String key() default "";

    String permitsPerSecond();

    /**
     * The maximum time waiting for getting the token.
     */
    String timeoutMs() default "1";

    String message() default "System is busy, please try again later";

}
