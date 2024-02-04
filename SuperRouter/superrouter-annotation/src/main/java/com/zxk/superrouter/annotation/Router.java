package com.zxk.superrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.superrouter.annotation
 * @Description
 * @Date： 2024-02-04 15:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Router {
    String path() default "";
}
