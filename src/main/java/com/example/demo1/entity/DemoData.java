package com.example.demo1.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.Table;

/**
 * @ClassName DemoData
 * @Date 2022/3/23 16:57
 * @Author chengshoufei
 * @Description TODO
 */
@Table(name = "user_do")
public class DemoData {
    @ExcelProperty(value = "编号", index = 0)
    private String id;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
