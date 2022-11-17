package com.code.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class SpringUtil {

    private static ApplicationContext applicationContext;

    public static String ACTIVITY_PROFILE;

    static {
        applicationContext = SpringContextHolder.getApplicationContext();
        ACTIVITY_PROFILE = applicationContext.getEnvironment().getActiveProfiles()[0].toLowerCase();
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        Assert.notNull(applicationContext, "applicationContext is null");
        applicationContext.publishEvent(event);
    }

    public static String getProfile() {
        return ACTIVITY_PROFILE;
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }

    public static boolean getProperty(String key, boolean defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, Boolean.class, defaultValue);
    }

    public static Integer getProperty(String key, Integer defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, Integer.class, defaultValue);
    }
}