package net.xdclass.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.request.VerifySignRequest;
import net.xdclass.service.SignAndEncryptService;
import net.xdclass.utils.signandencrypt.RSAUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName SignAndEncryptServiceImpl
 * @Description TODO
 * @Date 2022/3/5 14:40
 * @Version 1.0
 **/
@Service
@Slf4j
public class SignAndEncryptServiceImpl implements SignAndEncryptService {

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;

    @Override
    public void startProcess(VerifySignRequest vsr) {
        /**
         * 验签
         */
        boolean verify = false;
        try {
            System.out.println("验签开始===================");
            verify = RSAUtils.verify(vsr.getBizData().getBytes(), publicKey, vsr.getSign());
            if (verify) {
                System.out.println("验签结果：===================通过：：：");
            } else {
                System.out.println("验签结果：===================不通过：：：");
            }

        } catch (Exception e) {
            log.error("加签失败");
            e.printStackTrace();
        }
    }
}
