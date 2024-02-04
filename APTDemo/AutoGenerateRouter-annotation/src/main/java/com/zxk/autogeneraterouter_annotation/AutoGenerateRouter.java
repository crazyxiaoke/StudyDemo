package com.zxk.autogeneraterouter_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.autogeneraterouter_annotation
 * @Description
 * @Date： 2024-01-19 15:53
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface AutoGenerateRouter {
    String path() default "";
}
