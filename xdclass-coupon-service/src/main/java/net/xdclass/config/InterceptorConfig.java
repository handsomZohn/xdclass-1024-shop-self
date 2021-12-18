package net.xdclass.config;

import net.xdclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName InterceptorConfig
 * @Description 优惠券服务，拦截器开发
 * @Date 2021/12/6 21:31
 * @Version 1.0
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/coupon_record/*/**")
                .addPathPatterns("/api/coupon/*/**")

                //不拦截的路径
                .excludePathPatterns("/api/coupon/*/page_coupon");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
