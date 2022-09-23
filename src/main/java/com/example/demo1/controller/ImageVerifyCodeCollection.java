package com.example.demo1.controller;

import com.example.demo1.dictionaries.ProcessResult;
import com.example.demo1.entity.TArea;
import com.example.demo1.utils.image.ImageVerifyCodeGenerator;
import com.example.demo1.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ImageVerifyCodeCollection
 * @Date 2022/8/1 16:20
 * @Author chengshoufei
 * @Description TODO
 */
@RestController
@RequestMapping("/verify")
@Api(tags = "图片生成")
public class ImageVerifyCodeCollection {
    @ApiOperation("图片生成方法")
    @GetMapping("/code")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 生成验证码图片
        Map<String, Object> imageVerifyCode = ImageVerifyCodeGenerator.generate();
        BufferedImage bfm = (BufferedImage) imageVerifyCode.get("image");
        String code = String.valueOf(imageVerifyCode.get("code"));
        System.out.println("验证码:" + code);
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode", code);
        // 使用 ImageIO工具类将 图片验证码输出到响应流
        ImageIO.write(bfm, "PNG", response.getOutputStream());
    }

    @GetMapping("/codes")
    @ProcessResult
    public ResponseResult<TArea> verifyCode() {
        List<TArea> tAreas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TArea tArea = new TArea();
            tArea.setId("123" + i);
            tArea.setName("zhangsan" + i);
            tAreas.add(tArea);
        }

        return ResponseResult.success(tAreas);
    }
}
