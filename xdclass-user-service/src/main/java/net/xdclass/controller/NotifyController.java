package net.xdclass.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1")
public class NotifyController {

    @Autowired
    private Producer captchaProducer;

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public void getCapt(HttpServletRequest request, HttpServletResponse response) {
        String text = captchaProducer.createText();
        BufferedImage bufferedImage = captchaProducer.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("获取图形验证码异常:{}", e);
        }
        log.info("图形验证码：{}", text);
    }

    @GetMapping("/captcha/test")
    public JsonData getCaptTest(HttpServletRequest request, HttpServletResponse response) {
        String text = captchaProducer.createText();
        log.info("图形验证码：{}", text);
        return JsonData.buildSuccess(text);
    }
}
