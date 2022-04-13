package ps.demo.annotation;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.internal.http2.ErrorCode;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import ps.demo.dto.response.ErrorResponse;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.util.MyJsonUtil;
import ps.demo.util.MyTimeUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private final Map<String, RateLimiterWrap> limitMap = Maps.newConcurrentMap();

    @Around("@annotation(ps.demo.annotation.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null) {
//            String key = limitPropAwareConfig.resolveString(limit.key());
//            if (StringUtils.isBlank(key)) {
//                StringBuffer limitKey = new StringBuffer();
//                limitKey.append(joinPoint.getTarget().getClass().getName());
//                limitKey.append("-");
//                limitKey.append(signature.getName());
//                key = limitKey.toString().replaceAll("\\.", "\\-");
//            }
            String key = limit.key();

            if (!limitMap.containsKey(key)) {
                double permitsPerSecond = Double.parseDouble(limitPropAwareConfig.resolveString(limit.permitsPerSecond()));
                long timeoutMs = Long.parseLong(limitPropAwareConfig.resolveString(limit.timeoutMs()));
                String msg = limitPropAwareConfig.resolveString(limit.message());
                RateLimiterWrap rateLimiterWrap = RateLimiterWrap.builder()
                        .rateLimiter(RateLimiter.create(permitsPerSecond))
                        .timeoutMs(timeoutMs)
                        .message(msg).build();

                limitMap.put(key, rateLimiterWrap);
                log.info("Create new rateLimiterWrap={}", rateLimiterWrap);
            }
            RateLimiterWrap rateLimiterWrap = limitMap.get(key);
            boolean acquire = rateLimiterWrap.getRateLimiter().tryAcquire(rateLimiterWrap.getTimeoutMs(), TimeUnit.MILLISECONDS);
            if (!acquire) {
                log.debug("Bucket={}，get permits failed", key);
                throw new BadRequestException(CodeEnum.TOO_MANY_REQUESTS, true, rateLimiterWrap.getMessage());
                //this.responseFail(msg);
                //return null;
            }
        }
        return joinPoint.proceed();
    }

//    private void responseFail(String msg) throws IOException {
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.OK.value());
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .code(CodeEnum.TOO_MANY_REQUESTS.getCode()).build();
//        errorResponse.setMessage(msg);
//        errorResponse.setTimestamp(MyTimeUtil.getNowStr());
//        response.getWriter().write(MyJsonUtil.object2Json(errorResponse));
//    }

}
