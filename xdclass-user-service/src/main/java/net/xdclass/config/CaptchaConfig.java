package net.xdclass.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 图形验证码配置类
 * @author zzohn
 */
@Configuration
public class CaptchaConfig {

    /**
     * 默认验证码生成器
     * 此处的@Qualifier("captchaProducer")中的captchaProducer就是调用的地方注入对象的名称[这句话不对哦]
     * @return
     */
    @Bean
    @Qualifier("captchaProducer")
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        // 验证码个数
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");

        // 字体间隔
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "8");

        // 干扰线颜色
//		properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, "white");
        // 干扰实现类
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        // 图片样式
        // properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.WaterRipple");
        // 鱼眼模式
        // properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.FishEyeGimpy");

        /**
         * 阴影模式9
         */
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");

        // 文字来源
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
