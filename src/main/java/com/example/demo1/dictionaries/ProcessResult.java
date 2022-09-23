package com.example.demo1.dictionaries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName ProcessResult
 * @Date 2022/9/3 16:52
 * @Author chengshoufei
 * @Description TODO
 */
@Target(value = ElementType.METHOD )
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProcessResult {
}
