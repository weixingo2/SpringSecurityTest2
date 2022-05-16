package com.springSecurity.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils {

    public static Object getSessionAttribute(String key) {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession()
                .getAttribute(key);
    }

    public static void setSessionAttribute(String key, Object object) {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession().setAttribute(key, object);
    }
}
