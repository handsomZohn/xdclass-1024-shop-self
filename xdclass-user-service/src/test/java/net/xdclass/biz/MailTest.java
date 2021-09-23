package net.xdclass.biz;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.UserApplication;
import net.xdclass.component.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        mailService.sendMail("18310834045@163.com", "早晨杨老师起床后叫张毅峰也起床", "早啊，张毅峰，张毅峰早啊，张毅峰，张毅峰早啊，张毅峰，张毅峰早啊，张毅峰，张毅峰");
    }
}
