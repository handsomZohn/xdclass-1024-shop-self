package net.xdclass.utils.signandencrypt;

import java.util.Map;

/**
 * @ClassName RSATester
 * @Description 测试类
 * @Date 2022/3/5 11:39
 * @Version 1.0
 **/
public class RSATest {
    static String publicKey;
    static String privateKey;
    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
