package ps.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Collection;

@Slf4j
@Configuration
public class LimitPropAwareConfig implements ApplicationContextAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;
    private StringValueResolver stringValueResolver;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }


    public String resolveString(String strVal) {
        return this.stringValueResolver.resolveStringValue(strVal);
    }

    @PostConstruct
    public void validation() throws Exception {
        for (String bname : applicationContext.getBeanDefinitionNames()) {

            Object b = applicationContext.getBean(bname);
            Class clazz = b.getClass();
            if (org.springframework.aop.support.AopUtils.isAopProxy(b)) {
                clazz = org.springframework.aop.support.AopUtils.getTargetClass(b);
            }

            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Limit.class)) {
                    Limit limit = m.getAnnotation(Limit.class);
                    Double.parseDouble(this.resolveString(limit.permitsPerSecond()));
                    Long.parseLong(this.resolveString(limit.timeoutMs()));
                }
            }
        }

    }

}
