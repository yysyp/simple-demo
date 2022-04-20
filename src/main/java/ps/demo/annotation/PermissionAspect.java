package ps.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ps.demo.account.helper.MyPrincipalUtils;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Order(-2147483647)
@Slf4j
public class PermissionAspect {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     * <p>
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.mypocdemo.annotation.LoggerApply.*（..））com.mypocdemo.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.mypocdemo.annotation.*.*（..））com.mypocdemo.annotation包下的所有的类的所有的方法
     * 5、execution（* com.mypocdemo.annotation..*.*（..））com.mypocdemo.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.mypocdemo.annotation..*.*(String,?,Long)) com.mypocdemo.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(p.y.mypocdemo.base.annotation.MyPermission))
     */
    @Pointcut("@annotation(ps.demo.annotation.MyPermission)")
    private void cutMethod() {
    }

    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("---------------->>Mypermission check begins...");
        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        log.info("----------------->>permission check for methodName={}, params={}", methodName, params);
        MyPermission myPermission = getDeclaredAnnotation(joinPoint);

        if (!MyPrincipalUtils.hasRoles(myPermission.roles())) {
            throw new BadRequestException(CodeEnum.FORBIDDEN);
        }
        return joinPoint.proceed();

    }

    public MyPermission getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        MyPermission annotation = objMethod.getDeclaredAnnotation(MyPermission.class);
        // 返回
        return annotation;
    }

}
