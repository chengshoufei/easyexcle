package com.example.demo1.file;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @ClassName WaterMarkUtil
 * @Date 2022/5/30 18:05
 * @Author chengshoufei
 * @Description TODO
 */
public class WaterMarkUtil {

    /**
     * 为Excel打上水印工具函数 请自行确保参数值，以保证水印图片之间不会覆盖。
     *
     * @param wb              Excel Workbook
     * @param sheet           需要打水印的Excel
     * @param image           水印图片
     * @param startXCol       水印起始列
     * @param startYRow       水印起始行
     * @param betweenXCol     水印横向之间间隔多少列
     * @param betweenYRow     水印纵向之间间隔多少行
     * @param XCount          横向共有水印多少个
     * @param YCount          纵向共有水印多少个
     * @param waterMarkWidth  水印图片宽度为多少列
     * @param waterMarkHeight 水印图片高度为多少行
     * @throws IOException
     */
    public static void putWaterMarkToExcel(Workbook wb, Sheet sheet, BufferedImage image, int startXCol,
                                           int startYRow, int betweenXCol, int betweenYRow, int XCount, int YCount, int waterMarkWidth,
                                           int waterMarkHeight) throws IOException {


        // 加载图片
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

        if (null == image) {
            throw new RuntimeException("向Excel上面打印水印，读取水印图片失败(2)。");
        }
        ImageIO.write(image, "png", byteArrayOut);

        // 开始打水印
        Drawing drawing = sheet.createDrawingPatriarch();

        // 按照共需打印多少行水印进行循环
        for (int yCount = 0; yCount < YCount; yCount++) {
            // 按照每行需要打印多少个水印进行循环
            for (int xCount = 0; xCount < XCount; xCount++) {
                // 创建水印图片位置
                int xIndexInteger = startXCol + (xCount * waterMarkWidth) + (xCount * betweenXCol);
                int yIndexInteger = startYRow + (yCount * waterMarkHeight) + (yCount * betweenYRow);
                /*
                 * 参数定义： 第一个参数是（x轴的开始节点）； 第二个参数是（是y轴的开始节点）； 第三个参数是（是x轴的结束节点）；
                 * 第四个参数是（是y轴的结束节点）； 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                 * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）； 第七个参数是（图片宽度，共多少列）；
                 * 第8个参数是（图片高度，共多少行）；
                 */
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger,
                        yIndexInteger, xIndexInteger + waterMarkWidth, yIndexInteger + waterMarkHeight);

                Picture pic = drawing.createPicture(anchor,
                        wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                pic.resize();
            }
        }
    }

    /**
     * 给 Excel 添加水印
     *
     * @param workbook XSSFWorkbook
     */
    public static void insertWaterMarkTextToXlsx(XSSFWorkbook workbook, BufferedImage image) throws IOException {

        ByteArrayOutputStream imageOs = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imageOs);
        int pictureIdx = workbook.addPicture(imageOs.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG);
        XSSFPictureData pictureData = workbook.getAllPictures().get(pictureIdx);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
            XSSFSheet sheet = workbook.getSheetAt(i);
            PackagePartName ppn = pictureData.getPackagePart().getPartName();
            String relType = XSSFRelation.IMAGES.getRelation();
            PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
            sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
        }
    }


}
