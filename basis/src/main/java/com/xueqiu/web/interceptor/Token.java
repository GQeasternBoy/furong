package com.xueqiu.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 郭根权 on 2018/11/22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * 跳转方法上加@Token(create=true)
     * @return
     */
    boolean create() default false;

    /**
     * 在提交的action方法上加@Token(remove=true)
     */
    boolean remove() default false;
}
