package com.example.demo1.utils.file;

import com.github.xiaoymin.knife4j.core.util.StrUtil;

import java.io.*;
import java.util.*;

/**
 * @ClassName FileUtil
 * @Date 2022/9/16 9:40
 * @Author chengshoufei
 * @Description base64转文件 文件装base 限制300mb
 */
public class FileUtils {
    public static void main ( String[] args ) throws Exception {
        try {
            //每个text 生成字符数量
            Integer count = 10000000;

            String s = FileUtils.BASE64File ( "C:\\Users\\USER\\Desktop\\jar\\XML.rar" , "C:\\Users\\USER\\Desktop\\jar\\123\\" , "text" , count );
            System.out.println ( s );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
        //获取文件所在路径目录下名称
        String fileName = "C:\\\\Users\\\\USER\\\\Desktop\\\\jar\\\\123\\\\";
        //
        String filePath = "C:\\Users\\USER\\Desktop\\jar\\123\\";
        List < File > allFile = FileUtils.getAllFile ( fileName );
        //文件名称
        String filePathName = "jar";
        FileUtils.txtBase64 ( allFile , filePath , filePathName );
    }

    /**
     * path 读取文件路径 +文件名
     * filePath 生成文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String BASE64File ( String path , String filePath , String fileName , Integer count ) throws Exception {
        ByteArrayOutputStream os1 = new ByteArrayOutputStream ( );
        InputStream file1 = null;
        String base64toZip = "";
        try {
            file1 = new FileInputStream ( path );
            byte[] byteBuf = new byte[3 * 1024 * 1024];

            byte[] base64ByteBuf;
            //每次从文件中读取到的有效字节数
            int count1 = 0;

            while ((count1 = file1.read ( byteBuf )) != -1) {
                //如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                if ( count1 != byteBuf.length ) {
                    //从byteBuf中截取包含有效字节数的字节段
                    byte[] copy = Arrays.copyOf ( byteBuf , count1 );
                    //对有效字节段进行编码
                    base64ByteBuf = org.apache.commons.codec.binary.Base64.encodeBase64 ( copy );
                } else {
                    base64ByteBuf = org.apache.commons.codec.binary.Base64.encodeBase64 ( byteBuf );
                }
                os1.write ( base64ByteBuf , 0 , base64ByteBuf.length );
                os1.flush ( );

            }

            file1.close ( );
            base64toZip = os1.toString ( );

        }catch (Exception e) {
            throw new RuntimeException ( e );
        }

        if ( base64toZip.length ( ) > count ) {
            //创建文件目录
            int size = base64toZip.length ( ) / count;
            if ( base64toZip.length ( ) % count != 0 ) {
                size += 1;
            }
            List < String > list = new ArrayList <  > ( );
            for (int index = 0; index < size; index++) {
                String childStr = substring ( base64toZip , index * count , (index + 1) * count );
                list.add ( childStr );
            }
            for (int i = 0; i < list.size ( ); i++) {
                byte[] bytes = list.get ( i ).getBytes ( );

                System.out.println ( "C:\\Users\\USER\\Desktop\\jar\\123\\" + "test" + i + ".txt" );
                FileOutputStream fileOutputStream = new FileOutputStream ( filePath + i + ".txt" );
                fileOutputStream.write ( bytes );
                fileOutputStream.close ( );
            }
        } else {
            byte[] bytes = base64toZip.getBytes ( );

            FileOutputStream fileOutputStream = new FileOutputStream ( filePath + fileName + ".txt" );

            fileOutputStream.write ( bytes );
            fileOutputStream.close ( );
        }
        return "文件转base64字符成功";

    }

    public static String substring ( String str , int f , int t ) {
        if ( f > str.length ( ) ) {
            return null;
        }
        if ( t > str.length ( ) ) {
            return str.substring ( f );
        } else {
            return str.substring ( f , t );
        }
    }

    public static String txtBase64 ( List < File > allFile , String filePath , String filePathName ) throws Exception {
        Collections.sort ( allFile , new Comparator < File > ( ) {
            @Override
            public int compare ( File o1 , File o2 ) {
                if ( o1.isDirectory ( ) && o2.isFile ( ) ) {
                    return -1;
                }
                if ( o1.isFile ( ) && o2.isDirectory ( ) ) {
                    return 1;
                }
                Integer f = f ( o1.getName ( ) );
                Integer f2 = f ( o2.getName ( ) );
                return Integer.compare ( f , f2 );
            }
        } );
        StringBuffer stringBuffer = new StringBuffer ( );
        for (File files : allFile) {
            System.out.println ( files );
            FileInputStream fin = new FileInputStream ( files );
            InputStreamReader reader = new InputStreamReader ( fin );
            BufferedReader buffReader = new BufferedReader ( reader );
            String strTmp = "";
            while ((strTmp = buffReader.readLine ( )) != null) {
                stringBuffer.append ( strTmp );
            }
            buffReader.close ( );
        }

        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            File file = null;
            //创建文件目录
            File dir = new File ( filePath );
            if ( !dir.exists ( ) && !dir.isDirectory ( ) ) {
                dir.mkdirs ( );
            }

            byte[] bytes = Base64.getDecoder ( ).decode ( stringBuffer.toString ( ).getBytes ( ) );
            file = new File ( filePath + filePathName + ".rar" );
            fos = new java.io.FileOutputStream ( file );
            bos = new BufferedOutputStream ( fos );
            bos.write ( bytes );
        } catch (Exception e) {
            e.printStackTrace ( );
        } finally {
            if ( bos != null ) {
                try {
                    bos.close ( );
                } catch (IOException e) {
                    e.printStackTrace ( );
                }
            }
            if ( fos != null ) {
                try {
                    fos.close ( );
                } catch (IOException e) {
                    e.printStackTrace ( );
                }
            }
        }
        return "";
    }


    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFilePath 文件夹路径
     */
    public static List < File > getAllFile ( String dirFilePath ) {
        if ( StrUtil.isBlank ( dirFilePath ) ) {
            return null;
        }
        return getAllFile ( new File ( dirFilePath ) );
    }


    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFile 文件夹
     */
    public static List < File > getAllFile ( File dirFile ) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if ( Objects.isNull ( dirFile ) || !dirFile.exists ( ) || dirFile.isFile ( ) ) {
            return null;
        }
        File[] childrenFiles = dirFile.listFiles ( );
        if ( Objects.isNull ( childrenFiles ) || childrenFiles.length == 0 ) {
            return null;
        }
        List < File > files = new ArrayList <> ( );
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if ( childFile.isFile ( ) ) {
                files.add ( childFile );
            }
/*
            else {
                // 如果是文件夹，则将其内部文件添加进结果集合
                List<File> cFiles = getAllFile(childFile);
                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
                files.addAll(cFiles);
            }*/
        }
        return files;
    }

    static Integer f ( String filename ) {
        int x = filename.indexOf ( "." );
        String string2 = filename.substring ( 0 , x );
        char[] cs = string2.toCharArray ( );
        StringBuilder builder = new StringBuilder ( );
        for (int i = 0; i < cs.length; i++) {
            if ( Character.isDigit ( cs[i] ) ) {
                builder.append ( cs[i] );
            }
        }
        return Integer.parseInt ( builder.toString ( ) );
    }

}
