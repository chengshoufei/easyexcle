package com.example.demo1.file;

import com.example.demo1.entity.DownLoadRequest;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName execut
 * @Date 2022/6/14 19:19
 * @Author chengshoufei
 * @Description TODO
 */
public class execut {
    public void execute(HttpServletResponse response, HttpServletRequest request, DownLoadRequest downLoadRequest) throws IOException {
        String fileName = downLoadRequest.getFileName();
        if (StringUtils.contains(request.getHeader("USER-AGENT"), "Firefox") || request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        response.setContentType("multipart/form-data");
        // response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
    }
}
