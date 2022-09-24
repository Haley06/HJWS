package com.smallbell.modules.app.utils.request;

import java.lang.reflect.Field;

public class ClassCopyUtils {
    public ClassCopyUtils() {
    }

    public static <CopyProperty> void ClassCopy(Object dest, Object src) {
        Class<?> srcClass = src.getClass();
        Field[] srcClassDeclaredFields = srcClass.getDeclaredFields();
        Class<?> destClass = dest.getClass();
        Field[] destClassDeclaredFields = destClass.getDeclaredFields();
        Field[] var6 = destClassDeclaredFields;
        int var7 = destClassDeclaredFields.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Field destClassDeclaredField = var6[var8];
            destClassDeclaredField.setAccessible(true);
            String name = destClassDeclaredField.getName();
            if (destClassDeclaredField.isAnnotationPresent(com.hanzoy.utils.CopyProperty.class)) {
                name = ((com.hanzoy.utils.CopyProperty)destClassDeclaredField.getAnnotation(com.hanzoy.utils.CopyProperty.class)).value();
            }

            Field[] var11 = srcClassDeclaredFields;
            int var12 = srcClassDeclaredFields.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                Field srcClassDeclaredField = var11[var13];
                srcClassDeclaredField.setAccessible(true);
                if (srcClassDeclaredField.getName().equals(name)) {
                    try {
                        destClassDeclaredField.set(dest, srcClassDeclaredField.get(src));
                    } catch (IllegalAccessException var16) {
                        var16.printStackTrace();
                    }
                }
            }
        }

    }
}
