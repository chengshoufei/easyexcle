package com.example.demo1.file;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName ImageUtil
 * @Date 2022/5/30 18:06
 * @Author chengshoufei
 * @Description TODO
 */
public class ImageUtil {

    /**
     * @param content 水印内容
     * @throws IOException createby lyt
     */
    public static BufferedImage createWaterMark(String[] content) throws IOException {
        Integer width = 320;
        Integer height = 230;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
        Integer fontSize = 28;
        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 60)); //设置字体颜色和透明度
        g2d.setStroke(new BasicStroke(1)); // 设置字体
        g2d.setFont(font); // 设置字体类型  加粗 大小
        g2d.rotate(Math.toRadians(-10), (double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置旋转角度

        FontRenderContext context = g2d.getFontRenderContext();
        //找到水印信息中最长的
        int contentLindex = 0;
        int contentLength = content[0].length();

        for (int i = 0; i < content.length; i++) {
            if (content[i].length() > contentLength) {
                contentLindex = i;
            }
        }
        Rectangle2D bounds = font.getStringBounds(content[contentLindex], context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        // 写入水印文字原定高度过小，所以累计写水印，增加高度
        for (int i = 0; i < content.length; i++) {
            g2d.drawString(content[i], (int) x, (int) baseY);// 画出字符串
            baseY = baseY + font.getSize();
        }
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        return image;
    }


}
