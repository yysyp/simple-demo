package ps.demo.annotation;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import ps.demo.util.MyIpUtil;
import ps.demo.util.MyJsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class OperLogAspect {

    @Value("${version:1.0.0}")
    private String jarVersion;

    @Pointcut("@annotation(ps.demo.annotation.OperLog)")
    public void operLogPoinCut() {
    }

    @Pointcut("execution(* ps.demo..*Controller.*(..))")
    public void operExceptionLogPoinCut() {
    }

    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Before(value = "operLogPoinCut()")
    public void logStartTime(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
    }

    /**
     * No Exception throwing, normal return.
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void logOperationLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLog operlog = new OperationLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String operModul = opLog.operModul();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                operlog.setOperModul(operModul);
                operlog.setOperType(operType);
                operlog.setOperDesc(operDesc);
            }

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operlog.setOperMethod(methodName);

            Map<String, String> rtnMap = converMap(request.getParameterMap());
            String params = MyJsonUtil.object2Json(rtnMap);

            operlog.setOperRequParam(params);
            operlog.setOperRespParam(MyJsonUtil.object2Json(keys));
            operlog.setOperIp(MyIpUtil.getRealIp(request));
            operlog.setOperUri(request.getRequestURI());
            operlog.setOperCreateTime(new Date());

            log.debug("SimpleDemo LogAspect operation Log={}", operlog);
            log.info("SimpleDemo Api: {}, cost: {} ms", operlog.getOperDesc(), System.currentTimeMillis() - startTime.get());
        } catch (Exception e) {
            log.error("SimpleDemo OperLog Aspect error, err={}", e.getMessage(), e);
        } finally {
            startTime.set(null);
        }
    }


    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void logExceptionLog(JoinPoint joinPoint, Throwable e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog excepLog = new ExceptionLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            String params = MyJsonUtil.object2Json(rtnMap);
            excepLog.setExcRequParam(params);
            excepLog.setOperMethod(methodName);
            excepLog.setExcName(e.getClass().getName());
            excepLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            excepLog.setOperUri(request.getRequestURI());
            excepLog.setOperIp(MyIpUtil.getRealIp(request));

            excepLog.setOperCreateTime(new Date());

            log.info("SimpleDemo LogAspect exception Log={}", excepLog);

        } catch (Exception e2) {
            log.error("SimpleDemo OperLog Aspect error, err={}", e2.getMessage(), e2);
        } finally {
            startTime.set(null);
        }

    }


    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

}