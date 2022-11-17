package com.code.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (applicationContext != null) {
            try {
                return (T) applicationContext.getBean(name);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext != null) {
            try {
                return applicationContext.getBean(clazz);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}