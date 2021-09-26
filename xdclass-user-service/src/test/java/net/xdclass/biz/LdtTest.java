package net.xdclass.biz;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * localDateTime 测试类
 */
@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class LdtTest {

    @Test
    public void ldtTest(){
        LocalDateTime now = LocalDateTime.now();
        log.info("now:{}",now);

        log.info("dtf[yyyy/MM/dd]:{}", DateTimeFormatter.ofPattern("yyyy/MM/dd").format(now));
        log.info("dtf[yyyy-MM-dd]:{}", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now));
    }
}
