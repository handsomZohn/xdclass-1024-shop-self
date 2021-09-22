package net.xdclass.biz;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.UserApplication;
import net.xdclass.model.AddressDO;
import net.xdclass.service.AddressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 地址相关单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AddressTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void testAddressDetail(){
        AddressDO detail = addressService.detail(1L);
        log.info(detail.toString());
        Assert.assertNotNull(detail);
    }
}
