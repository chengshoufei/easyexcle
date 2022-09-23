package com.example.demo1.log;

import java.lang.annotation.*;

/**
 * @ClassName Log
 * @Date 2022/3/26 16:27
 * @Author chengshoufei
 * @Description TODO
 */
//方法参数 方法
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default "";
}
