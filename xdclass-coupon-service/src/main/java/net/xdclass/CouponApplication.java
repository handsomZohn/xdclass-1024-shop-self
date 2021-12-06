package net.xdclass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName CouponApplication
 * @Description 优惠券服务启动类
 * @Date 2021/12/6 17:05
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan("net.xdclass.mapper")
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }
}
