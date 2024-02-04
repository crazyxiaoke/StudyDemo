package com.zxk.apt_annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.apt_annotation
 * @Description
 * @Date： 2024-01-18 14:27
 */
@Inherited
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface AptDemoAnnotation {

    String desc() default "";
}
