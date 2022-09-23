package com.example.demo1.file;

import java.io.*;

/**
 * @ClassName FileUtils
 * @Date 2022/5/9 15:31
 * @Author chengshoufei
 * @Description 生成指定文件
 */
public class FileUtils {


    /*
     bfile byte[]
    filePath 文件地址
    fileName 文件名称 *.txt,*.json
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            if (fileName.indexOf(".") <= -1) {
                return;
            }
            file = new File(filePath + File.separator + fileName);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bfile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*public static void main(String[] args) {
        String  a="212313313";
        try {
            FileUtils.getFile(a.getBytes("UTF8"),"F:\\asdasda","zas.java");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*/
}


