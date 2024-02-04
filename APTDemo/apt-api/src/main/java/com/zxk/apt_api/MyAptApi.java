package com.zxk.apt_api;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.apt_api
 * @Description
 * @Date： 2024-01-18 15:43
 */
public class MyAptApi {

    public static void init(Activity activity) {
        try {
            Class clazz = Class.forName(activity.getClass().getName() + "_ViewBinding");
            Constructor constructor = clazz.getDeclaredConstructor();
            Object o = constructor.newInstance();
            Method method = clazz.getDeclaredMethod("test", String.class);
            method.invoke(o, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
