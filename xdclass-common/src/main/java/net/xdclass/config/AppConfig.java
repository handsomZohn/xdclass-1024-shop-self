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

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private String redisPort;
    @Value("${spring.redis.password}")
    private String redisPwd;


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
}
