package com.threec.common.mybatis.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtil {
    private static MessageSource messageSource = (MessageSource) SpringContextUtils.getBean("messageSource");

    public MessageUtil() {
    }

    public static String getMessage(int code) {
        return getMessage(code);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }
}
