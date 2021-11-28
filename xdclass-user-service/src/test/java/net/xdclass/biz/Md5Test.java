package net.xdclass.biz;


import lombok.extern.slf4j.Slf4j;
import net.xdclass.UserApplication;
import net.xdclass.utils.CommonUtil;
//import org.junit.jupiter.api.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class Md5Test {

    @Test
    public void TestMd5(){
        log.info(CommonUtil.MD5("123456"));
    }
}
