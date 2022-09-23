package com.example.demo1.dictionaries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName SexValue
 * @Date 2022/9/3 16:52
 * @Author chengshoufei
 * @Description TODO
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  SexValue {
    String value() default "";
}
