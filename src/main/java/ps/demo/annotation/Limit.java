package ps.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {

    String permitsPerSecond();

    String key() default "";

    /**
     * The maximum time waiting for getting the token.
     */
    String timeoutMs() default "0";

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    String msg() default "System is busy, please try again later";

}
