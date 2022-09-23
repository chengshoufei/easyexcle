package com.example.demo1.utils.base;

import java.io.*;
import java.util.Base64;

/**
 * @ClassName Base64Util
 * @Date 2022/9/20 10:38
 * @Author chengshoufei
 * @Description TODO
 */
public class Base64Util {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\USER\\Desktop\\jar\\devecostudio-windows-tool-2.1.0.501.zip";

        String fileContent=fileToString(filePath);
        System.out.println(fileContent);
    //    String  outFilePath = "F:\\copy\\"+System.currentTimeMillis()+ File.separator +"测试.docx";
     //   Boolean is= stringToFile(fileContent,outFilePath);

    }

    public static String fileToString(String filePath){
        InputStream  input=null;
        try {
            File file = new File(filePath);
            input= new FileInputStream(file);
            byte[] buffer = toByteArray(input);
            // 读到buffer字节数组中
            input.read(buffer);

            String fileContent=  Base64.getEncoder().encodeToString(buffer);

            return fileContent;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    public static boolean stringToFile(String fileContent, String filePath) {
        boolean flag = true;
        byte[] bytes = Base64.getDecoder().decode(fileContent);

        //测试数据的准确性
        InputStream input=null;
        OutputStream output=null;
        try {
            File file = new File(filePath).getParentFile();
            if(!file.exists()){
                file.mkdirs();
            }
            input = new ByteArrayInputStream(bytes);
            output=new FileOutputStream(filePath);
            byte[] buf =new byte[1024*8];
            int n;
            while((n=input.read(buf))!=-1){
                output.write(buf, 0,n);
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            return flag;
        }finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
