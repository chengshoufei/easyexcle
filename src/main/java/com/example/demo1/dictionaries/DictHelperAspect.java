package com.example.demo1.dictionaries;

import com.example.demo1.utils.ResponseResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName DictHelperAspect
 * @Date 2022/9/3 11:20
 * @Author chengshoufei
 * @Description TODO
 */
@Aspect
@Configuration
public class DictHelperAspect {
    private final Logger log = LoggerFactory.getLogger(DictHelperAspect.class);


    @AfterReturning(value = "@annotation(processResult)", returning = "result")
    public Object doAroundJoinPoint(JoinPoint joinPoint, ProcessResult processResult, Object result) throws IllegalAccessException {

        if (result == null) {
            return null;
        }
        if (result instanceof ResponseResult) {
            if (result == null) {
                return null;
            }
            if (result instanceof Collection) {
                for (Object obj : (Collection<?>) result) {
                    //处理
                    processObj(obj);
                }
            } else {
                //处理
                processObj(result);
            }
        } else if (result instanceof Collection) {
            //处理0..0
            for (Object obj : (Collection<?>) result) {
                //处理
                processObj(obj);
            }
        } else {
            processObj(result);

        }
        return result;

    }

    private void processObj(Object obj) throws IllegalAccessException {
        //取出Obj所有 Field，以及父类 Field
        ResponseResult responseResult = (ResponseResult) obj;
        try {
            Class<? extends ResponseResult> aClass = responseResult.getClass();
            Field  data = aClass.getDeclaredField("data");
            data.setAccessible(true);
            List o = (List) data.get(responseResult);
            for (Object o2 : o) {
                Class<?> aClass1 = o2.getClass();
                Field[] declaredFields = aClass1.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(SexValue.class)) {
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(o2));
                        log.info("fieldValue:{}", fieldValue);
                        if ("zhangsan1".equals(fieldValue)) {
                            field.set(o2, "男");
                        } else {
                            field.set(o2, "女");
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
