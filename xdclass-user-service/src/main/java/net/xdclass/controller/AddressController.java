package net.xdclass.controller;


import net.xdclass.exception.BizException;
import net.xdclass.model.AddressDO;
import net.xdclass.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地址控制类
 */
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/find/{address_id}")
    public Object detail(@PathVariable("address_id") long address_id) {
        AddressDO detail = addressService.detail(address_id);
        // int i = 0;

        // 模拟抛出一个全局异常
        // int j = 1 / 0;

        // 模拟抛出一个业务异常
        if (address_id == 1) {
            throw new BizException(900, "测试自定义业务异常");
        }
        return detail;
    }
}

