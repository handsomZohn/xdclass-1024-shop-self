package net.xdclass.controller;

import io.swagger.annotations.Api;
import net.xdclass.service.SignAndEncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SignAndEncryptController
 * @Description TODO
 * @Date 2022/3/5 14:37
 * @Version 1.0
 **/

@Api(tags = "签名测试模块-加签")
@RestController
@RequestMapping("/api/sign/v1")
public class SignAndEncryptController {

    @Autowired
    SignAndEncryptService signAndEncryptService;

    @GetMapping("/start")
    public void startProcess(){
        signAndEncryptService.startProcess();
    }
}
