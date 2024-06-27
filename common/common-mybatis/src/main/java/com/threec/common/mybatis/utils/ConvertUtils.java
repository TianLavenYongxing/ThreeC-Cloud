package com.threec.common.mybatis.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConvertUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        } else {
            List targetList = new ArrayList(sourceList.size());

            try {
                Iterator var3 = sourceList.iterator();

                while (var3.hasNext()) {
                    Object source = var3.next();
                    T targetObject = target.newInstance();
                    BeanUtils.copyProperties(source, targetObject);
                    targetList.add(targetObject);
                }
            } catch (Exception var6) {
                logger.error("convert error ", var6);
            }

            return targetList;
        }
    }

    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        } else {
            T targetObject = null;

            try {
                targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
            } catch (Exception var4) {
                logger.error("convert error ", var4);
            }

            return targetObject;
        }
    }

    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target, String... fieldNames) {
        if (sourceList == null) {
            return null;
        } else {
            List targetList = new ArrayList(sourceList.size());
            String language = getLanguage();

            try {
                Iterator var5 = sourceList.iterator();

                while (var5.hasNext()) {
                    Object source = var5.next();
                    T targetObject = target.newInstance();
                    BeanUtils.copyProperties(source, targetObject);
                    String[] var8 = fieldNames;
                    int var9 = fieldNames.length;

                    for (int var10 = 0; var10 < var9; ++var10) {
                        String fieldName = var8[var10];
                        setFieldValueByFieldName(fieldName, targetObject, getFieldValueByFieldName(fieldName + language, source));
                    }

                    targetList.add(targetObject);
                }
            } catch (Exception var12) {
                logger.error("convert error ", var12);
            }

            return targetList;
        }
    }

    private static void setFieldValueByFieldName(String fieldName, Object object, String value) {
        try {
            Class c = object.getClass();
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, value);
        } catch (Exception var5) {
        }

    }

    public static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object).toString();
        } catch (Exception var3) {
            return null;
        }
    }

    public static String getLanguage() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String acceptLanguage = request.getHeader("Accept-Language");
        String language = null;
        if (StringUtils.isNotBlank(acceptLanguage)) {
            language = acceptLanguage.split(";")[0];
        }

        if ("en-US".equals(language)) {
            language = "En";
        } else if ("zh-CN".equals(language)) {
            language = "Zh";
        } else {
            language = "Zh";
        }

        return language;
    }
}
