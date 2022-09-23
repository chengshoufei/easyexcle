package com.example.demo1.entity;

/**
 * @ClassName EntityTest
 * @Date 2022/4/7 15:06
 * @Author chengshoufei
 * @Description TODO
 */
public class EntityTest {
    public static void main(String[] args) {
        BeanToGenerateTable bean = new BeanToGenerateTable();
        String sql = bean.generateTableOracle("com.example.demo1.entity.DemoData", true);
        System.err.println(sql);


    }
}
