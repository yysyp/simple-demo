package ps.demo.annotation;

import java.lang.annotation.*;

/**
 * @author yunpeng.song
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String operModul() default "";

    String operType() default "";

    String operDesc() default "";
}
