package net.xdclass.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzohn
 * @ClassName AppConfig
 * @Description TODO
 * @Date 2022/1/10 12:45
 * @Version 1.0
 **/
@Configuration
@Data
public class AppConfig {
    // 新年新气象，愿你平安又吉祥~

    /**
     * 这个配置类是写到common里面的，就是公共的工程，它是怎么读取到xdclass-coupon-service里面的配置文件的？
     */
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private String redisPort;
    @Value("${spring.redis.password}")
    private String redisPwd;

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机方式
        config.useSingleServer().setPassword(redisPwd).setAddress("redis://" + redisHost + ":" + redisPort);

        // 集群方式
        // config.useClusterServers().addNodeAddress("redis://192.31.21.1:6379","redis://192.31.21.2:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    // 租赁物信息变更审批结果
    // Approval Results of Leased Property Information Change
    //leased_info_change_approval_result

    // 租赁物信息变更审批状态描述

    // 扣款银行卡变更申请状态
    // deduction_bank_card_change_application_status
    // deduction_bank_card_change_stat
    // ?purchase_price

}
