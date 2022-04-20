package ps.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPermission {

    /**
     * public static final String ROLE_ADMIN = "ROLE_ADMIN";
     * public static final String ROLE_USER = "ROLE_USER";
     * @return
     */
    String[] roles();

}
