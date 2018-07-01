package com.zcj.ls.ls_web.utils;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext app) {
        applicationContext = app;
    }
}
