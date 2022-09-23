package com.example.demo1.file;

import com.alibaba.excel.util.StringUtils;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

/**
 * @ClassName ExcelUtils
 * @Date 2022/5/30 17:23
 * @Author chengshoufei
 * @Description 通用导出exce
 */
public class ExcelUtils {


    public static void excelExport ( HttpServletResponse response , String fileTitile , LinkedList < Map < String, Object > > list , Map < String, String > custCols , String watermark ) {
        OutputStream os = null;
        XSSFWorkbook wbook = null;
        XSSFSheet sheet = null;
        try {
            wbook = new XSSFWorkbook ( );
            String value = "";
            String title = "";
            for (Map.Entry < String, String > stringStringEntry : custCols.entrySet ( )) {
                title += stringStringEntry.getValue ( ) + ",";
                value += stringStringEntry.getKey ( ) + ",";
            }
            title = title.substring ( 0 , title.length ( ) - 1 );
            title = new String ( title.getBytes ( "gb2312" ) , "gbk" );

            String[] titles = title.split ( "," );
            String[] valus = value.split ( "," );
            String fileName = new String ( fileTitile.getBytes ( "gb2312" ) , StandardCharsets.ISO_8859_1 ); // 乱码解决
            os = response.getOutputStream ( );// 取得输出流
            response.reset ( );// 清空输出流
            response.setHeader ( "Content-disposition" , "attachment; filename=" + fileName + ".xlsx" );// 设定输出文件头
            response.setContentType ( "application/msexcel;charset=utf-8" );// 定义输出类型
            response.setCharacterEncoding ( "utf-8" );
            //response.setContentType("application/vnd.ms-excel;charset=GB18030");// 定义输出类型
            sheet = wbook.createSheet ( fileTitile );// sheet名称
            // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            XSSFRow row0 = sheet.createRow ( 0 );
            //设置一个标题样式
            XSSFCellStyle titleStyle = wbook.createCellStyle ( );
            XSSFFont titleFont = wbook.createFont ( );

            titleFont.setFontHeightInPoints ( (short) 12 ); //字体大小
            titleFont.setFontName ( "宋体" ); //什么字体
            titleFont.setItalic ( false ); //是不倾斜
            titleFont.setStrikeout ( false ); //是不是划掉
            titleStyle.setFont ( titleFont );
//            titleStyle.setFillForegroundColor(HSSFColor.RED.index);//添加前景色,内容看的清楚
            //生成标题列
            for (int i = 0; i < titles.length; i++) {
                // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
                XSSFCell titleCell = row0.createCell ( i );
                // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
                // 设置单元格内容
                titleCell.setCellValue ( titles[i] );
                titleCell.setCellStyle ( titleStyle );
                sheet.setDefaultColumnWidth ( 23 );
            }

            if ( list != null && !list.isEmpty ( ) ) {
                for (int j = 0; j < list.size ( ); j++) {
                    Map < String, Object > obj = list.get ( j );
                    XSSFRow row = sheet.createRow ( j + 1 );
                    for (int i = 0; i < titles.length; ++i) {
                        XSSFCell cell = row.createCell ( i );
                        String attr = String.valueOf ( obj.get ( valus[i] ) );
                        if ( ("null".equals ( attr )) || (StringUtils.isEmpty ( attr )) ) {
                            attr = "";

                        }

                        cell.setCellValue ( attr );
                    }
                }
            }

            //主体内容生成结束
            //添加水印开始  计算水印位置 个数，可以自行更改
            System.out.println ( "jinru =================================" );
            String[] split = watermark.split ( "," );
            BufferedImage image = ImageUtil.createWaterMark ( split );
            WaterMarkUtil.insertWaterMarkTextToXlsx ( wbook , image );
            wbook.write ( os ); // 写入文件
        } catch (Exception ex) {
            ex.printStackTrace ( );
        } finally {
            if ( os != null ) {
                try {
                    os.close ( );  // 关闭流
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
            }
        }

    }


}




