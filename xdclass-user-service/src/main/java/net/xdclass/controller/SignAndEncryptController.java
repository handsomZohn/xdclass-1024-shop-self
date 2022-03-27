package net.xdclass.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.request.VerifySignRequest;
import net.xdclass.service.SignAndEncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SignAndEncryptController
 * @Description TODO
 * @Date 2022/3/5 14:37
 * @Version 1.0
 **/

@Slf4j
@Api(tags = "签名测试模块-验签")
@RestController
@RequestMapping("/api/sign/v2")
public class SignAndEncryptController {

    @Autowired
    SignAndEncryptService signAndEncryptService;

    @PostMapping("/verify")
    public void startProcess(@RequestBody VerifySignRequest vsr) {
        System.out.println("签名测试模块-验签::======================================");
        signAndEncryptService.startProcess(vsr);
    }
}
