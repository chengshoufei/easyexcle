package com.example.demo1.controller;

import com.alibaba.excel.EasyExcel;
import com.example.demo1.entity.DemoData;
import com.example.demo1.entity.TArea;
import com.example.demo1.file.ExcelUtils;
import com.example.demo1.file.UploadUtil;
import com.example.demo1.log.Log;
import com.example.demo1.service.impl.BookInfoExcelListener;
import com.example.demo1.utils.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Date 2022/3/23 17:07
 * @Author chengshoufei
 * @Description TODO
 */
@RestController
@RequestMapping("/test")
@Api(tags = "用户")
public class TestController {
    @Autowired
    private BookInfoExcelListener bookInfoExcelListener;

    @RequestMapping("/file")
    @Log(value = "日志记录")
    public void doFile ( @RequestParam("file") MultipartFile file ) {
        try {
            EasyExcel.read ( file.getInputStream ( ) , DemoData.class , bookInfoExcelListener ).sheet ( ).headRowNumber ( 1 ).doRead ( );
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }

    @RequestMapping("/files")
    @Log(value = "上传文件到服务器")
    public String upload ( @RequestParam("files") MultipartFile file ) {
        try {
            String upload = UploadUtil.upload ( file );
            return upload;
        } catch (IOException e) {
            e.printStackTrace ( );
            return "shibai";
        }
    }


    @RequestMapping(value = "/Excelfiles", method = RequestMethod.GET)
    @Log(value = "通用导出excel")
    public String Excelfiles ( HttpServletResponse response ) {
        //导出的数据
        LinkedList < Map < String, Object > > list = new LinkedList <> ( );
        //key 实体名 value excel 头
        Map < String, String > listmap = new HashMap <> ( );

        ExcelUtils.excelExport ( response , "导出文件名" , list , listmap , "设置水印多个用,分割" );
        return null;
    }

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/getArea", method = RequestMethod.GET)
    @Log(value = "查询省市县")
    @ApiOperation("查询省市")
    public List < TArea > getArea ( @RequestParam("pid") String pid ) {
        List < TArea > a = bookInfoExcelListener.getArea ( pid );
        //redisUtil.set("arear", JSON.toJSONString(a));
        return a;
    }


}
