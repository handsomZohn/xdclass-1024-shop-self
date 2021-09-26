package net.xdclass.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.NotifyService;
import net.xdclass.utils.CommonUtil;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1")
public class NotifyController {

    @Autowired
    @Qualifier("captchaProducer")// 注入指定的captchaProducer
    private Producer captchaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NotifyService notifyService;

    /**
     * 临时使用10分钟有效，方便测试
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public void getCapt(HttpServletRequest request, HttpServletResponse response) {
        String text = captchaProducer.createText();
        log.info("图形验证码：{}", text);

        redisTemplate.opsForValue().set(getCaptchaKey(request), text, CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

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
    }


    @ApiOperation("发送邮箱用户注册验证码")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@RequestParam(value = "to") String to,
                                     @RequestParam(value = "captcha") String captcha,
                                     HttpServletRequest request) {

        String captchaKey = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(captchaKey);


        if (captcha != null && cacheCaptcha != null && captcha.equalsIgnoreCase(cacheCaptcha)) {
            redisTemplate.delete(captchaKey);
            // 校验图形验证码通过，发送邮箱验证码
            JsonData jsonData = notifyService.sendCode(SendCodeEnum.USER_REGISTER, to);
            return jsonData;

        } else {
            // 校验图形验证码不通过
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }

    }

    @GetMapping("/captcha/test")
    public JsonData getCaptTest(HttpServletRequest request, HttpServletResponse response) {
        String text = captchaProducer.createText();
        log.info("图形验证码：{}", text);
        return JsonData.buildSuccess(text);
    }

    /**
     * 获取图形验证码的key key值的组成：
     * 服务名：功能名称：随机8位
     * e.g. user-service:captcha:把ip和浏览器签名加密后取出8位
     * @param request
     * @return
     */
    private String getCaptchaKey(HttpServletRequest request) {
        String ipAddr = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        // key 规范：业务划分，冒号隔离
        // 防止ip漂移 需要加上IP地址和浏览器的指纹（User-Agent）
        String key = "user-service:captcha:" + CommonUtil.MD5(ipAddr + userAgent);

        log.info("ip={}", ipAddr);
        log.info("userAgent={}", userAgent);
        log.info("key={}", key);

        return key;
    }
}
