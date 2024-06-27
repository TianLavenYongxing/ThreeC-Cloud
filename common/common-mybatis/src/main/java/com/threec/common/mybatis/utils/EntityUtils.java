package com.threec.common.mybatis.utils;


import com.threec.common.mybatis.enums.DelFlagEnum;

import java.lang.reflect.Field;
import java.util.*;

public class EntityUtils {
    public EntityUtils() {
    }

    public static <T> List<T> deletedBy(Long[] ids, Class<T> entity) {
        List<T> entityList = new ArrayList(ids.length);
        Long[] var3 = ids;
        int var4 = ids.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Long id = var3[var5];
            T entityObject = deletedBy(id, entity);
            entityList.add(entityObject);
        }

        return entityList;
    }

    public static <T> T deletedBy(Long id, Class<T> entity) {
        Map<String, Object> map = new HashMap(4);
        map.put("id", id);
        map.put("updateBy", 1);   //todo 后期完善 去获取系统用户id
        map.put("updateTime", new Date());
        map.put("delFlag", DelFlagEnum.DEL.value());
        T entityObject = null;

        try {
            entityObject = entity.newInstance();
        } catch (Exception var6) {
        }

        Iterator var4 = map.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var4.next();
            setValue(entityObject, entry.getKey(), entry.getValue());
        }

        return entityObject;
    }

    private static <T> void setValue(T entity, String key, Object value) {
        for (Class<?> clazz = entity.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            Field[] var5 = fields;
            int var6 = fields.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(key)) {
                    try {
                        field.set(entity, value);
                        return;
                    } catch (IllegalAccessException var10) {
                    }
                }
            }
        }

    }
}