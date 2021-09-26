package net.xdclass.config;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 要拦截的路径
                .addPathPatterns("//api/user/*/**", "/api/address/*/**")

                // 盘查不拦截的路径
                .excludePathPatterns(
                        "/api/user/*/send_code",
                        "/api/user/*/captcha",
                        "/api/user/*/register",
                        "/api/user/*/login",
                        "/api/user/*/upload"
                );
    }
}
