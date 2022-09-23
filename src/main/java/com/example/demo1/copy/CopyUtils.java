package com.example.demo1.copy;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @ClassName CopyUtils
 * @Date 2022/4/20 21:31
 * @Author chengshoufei
 * @Description TODO
 */
public class CopyUtils {


    /**
     * 从List<A> copy到List<B>
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copy(List<?> list, Class<T> clazz) {
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

    /**
     * 从对象A copy到 对象B
     *
     * @param ob    A
     * @param clazz B.class
     * @return B
     */
    public static <T> T copy(Object ob, Class<T> clazz) {
        String oldOb = JSON.toJSONString(ob);
        return JSON.parseObject(oldOb, clazz);


    }


}
