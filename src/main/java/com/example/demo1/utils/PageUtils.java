package com.example.demo1.utils;

import java.util.List;

/**
 * @ClassName VerificationCodeUtil
 * @Date 2022/7/29 14:50
 * @Author chengshoufei
 * @Description 分页工具
 */
public class PageUtils {

    /**
     * 开始分页
     *
     * @param list
     * @param pageNum  页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static <T> List<T> startPage(List list, int pageNum, int pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        int count = list.size(); // 记录总数
        int pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        if (pageNum > pageCount) {
            return null;
        }
        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        if (fromIndex > count || toIndex > count) {
            return null;
        }
        return list.subList(fromIndex, toIndex);
    }

}
