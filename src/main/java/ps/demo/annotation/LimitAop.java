package ps.demo.annotation;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LimitAop {

    @Autowired
    private LimitPropAwareConfig limitPropAwareConfig;

    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * Use different key for different API.
     */
    private final Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Around("@annotation(ps.demo.annotation.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null) {
            String key = limitPropAwareConfig.resolveString(limit.key());
            if (StringUtils.isBlank(key)) {
                StringBuffer limitKey = new StringBuffer();
                limitKey.append(joinPoint.getTarget().getClass().getName());
                limitKey.append(".");
                limitKey.append(signature.getName());
                key = limitKey.toString();
            }
            double permitsPerSecond = Double.parseDouble(limitPropAwareConfig.resolveString(limit.permitsPerSecond()));
            long timeoutMs = Long.parseLong(limitPropAwareConfig.resolveString(limit.timeoutMs()));
            String msg = limitPropAwareConfig.resolveString(limit.msg());

            RateLimiter rateLimiter = null;
            if (!limitMap.containsKey(key)) {
                log.info("--limit per second={}", permitsPerSecond);
                rateLimiter = RateLimiter.create(permitsPerSecond);
                limitMap.put(key, rateLimiter);
                log.info("Create new bucket={}，size={}", key, permitsPerSecond);
            }
            rateLimiter = limitMap.get(key);
            boolean acquire = rateLimiter.tryAcquire(timeoutMs, limit.timeunit());
            if (!acquire) {
                log.debug("Bucket={}，get permits failed", key);
                this.responseFail(msg);
                return null;
            }
        }
        return joinPoint.proceed();
    }

    private void responseFail(String msg) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().write(msg);
    }

}
