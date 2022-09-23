package com.example.demo1.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName LogAspect  实体类转 orcle 创建语句
 * @Date 2022/3/26 16:31
 * @Author chengshoufei
 * @Description TODO
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {""};

    // 配置织入点
    @Pointcut("@annotation(Log)")
    public void logPointCut() {
    }

    //切面 配置通知
    @AfterReturning(pointcut = "logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        try {
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            System.out.println("获取的方法有：" + method);
            Log annotation = method.getAnnotation(Log.class);
            if (annotation != null) {
                String value = annotation.value();
                System.out.println("操作方法名：" + value);
            }
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = joinPoint.getSignature().getName();
            System.out.println("获取请求的类名" + className);
            System.out.println("获取请求的方法名有：" + methodName);
            //请求的参数
            Object[] args = joinPoint.getArgs();
            //过滤HttpServletRequest和HttpServletResponse类型的参数
            List<Object> logArgs = streamOf(args)
                    .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                    .collect(Collectors.toList());
            //将参数所在的数组转换成json
            try {
                String argStr = JSON.toJSONString(logArgs);
                System.out.println("获取请求参数" + argStr);
            } catch (Exception e) {
                System.out.println("获取请求参数" + args);
            }


            //后去日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String creationDate = df.format(new Date());
            System.out.println("创建日期" + creationDate);
            HttpServletRequest request = requestAttributes.getRequest();
            String ipAddr = IPIpUtil.getIpAddr(request);
            System.out.println(ipAddr);
        } catch (Exception e) {
            log.error("==前置通知异常==");
            log.error("异常信息:{}", e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter() {
        return new PropertyPreFilters().addFilter().addExcludes(EXCLUDE_PROPERTIES);
    }


    public static <T> Stream<T> streamOf(T[] array) {
        return ArrayUtils.isEmpty(array) ? Stream.empty() : Arrays.asList(array).stream();
    }
}
