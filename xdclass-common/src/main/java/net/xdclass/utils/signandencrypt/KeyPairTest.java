package net.xdclass.utils.signandencrypt;

import java.util.Map;

/**
 * @ClassName KeyPairTester
 * @Description 测试类
 * @Date 2022/3/5 11:39
 * @Version 1.0
 **/
public class KeyPairTest {
    static String publicKey;
    static String privateKey;
    static {
        try {
            Map<String, Object> keyMap = KeyPairUtils.genKeyPair();
            publicKey = KeyPairUtils.getPublicKey(keyMap);
            privateKey = KeyPairUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
