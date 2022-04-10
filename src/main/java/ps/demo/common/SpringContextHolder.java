package ps.demo.common;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }


    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }


    public static void clearHolder() {
        applicationContext = null;
    }


    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        applicationContext = appContext;
    }


    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }


    private static void assertContextInjected() {
        Validate.validState(applicationContext != null, "applicationContext has not been injected.");
    }
}
