package com.example.demo1.utils.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName URLDemo
 * @Date 2022/8/2 16:45
 * @Author chengshoufei
 * @Description TODO
 */
public class URLDemo {
    public static void main(String[] args) {
        //网址为    http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input
        String strurl = "https://uland.taobao.com/sem/tbsearch?keyword=%E6%B7%98%E5%AE%9D%E9%A6%99%E5%BD%B1&refpid=mm_26632258_3504122_32538762&clk1=2d6eb24d0ec9b137f07aaf7e0741b043&upsId=2d6eb24d0ec9b137f07aaf7e0741b043";
        //建立url爬取核心对象
        try {
            URL url = new URL(strurl);
            //通过url建立与网页的连接
            URLConnection conn = url.openConnection();
            //通过链接取得网页返回的数据
            InputStream is = conn.getInputStream();

            System.out.println(conn.getContentEncoding());
            //一般按行读取网页数据，并进行内容分析
            //因此用BufferedReader和InputStreamReader把字节流转化为字符流的缓冲流
            //进行转换时，需要处理编码格式问题
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            //按行读取并打印
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}



