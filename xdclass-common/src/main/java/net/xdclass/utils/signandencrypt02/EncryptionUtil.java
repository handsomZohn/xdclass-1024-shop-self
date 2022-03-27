package net.xdclass.utils.signandencrypt02;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @ClassName EncryptionUtil
 * @Description 加密
 * @Date 2022/2/24 9:52
 * @Version 1.0
 **/
@Slf4j
public class EncryptionUtil {


    //RSA 公钥私钥
    //公钥
    public static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOyAK09+zVUTaLcB9otiOQvO3Jkhp6JgE15dXkpqDnQBtiZeIujSDAvSn9KlmAQ/KwpoLiVte3boUbFBybBuTFECAwEAAQ==";
    //私钥
    public static final String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA7IArT37NVRNotwH2i2I5C87cmSGnomATXl1eSmoOdAG2Jl4i6NIMC9Kf0qWYBD8rCmguJW17duhRsUHJsG5MUQIDAQABAkEAlEw+XEfP66QTCoaxmW9LBCt/yon++uOx4X88q/7a90QSwD00VUUoxa3hzx5E/LtSgBrZIpHnevw0VWpQn5U8vQIhAP2xVcVN0/wOvU6r5EClKPDkL+lV62vLgH7Aoi2TaSj3AiEA7qbOcMOq9HFiJFlbUhUlt+g6ap1Xhq2Rs+UGwdIq6vcCICjmnFNxFso+fhre76/UcONVhrvx1EDO0GqfGAaNC5lFAiB8Xv3zEmXDYluNKexHiLHRd/sJmNoGW+o04ER3OfoX3QIhAKEsoUoPIJlbgyC5vxIEkRdAyhB6TDo9UhCJP9OSV/At";

    public static void main(String[] args) throws Exception {
        //要加密的业务数据
        String bizData = "[{\"inicators\": [{\"props\": [], \"indicatorCode\": \"CMN00013\", \"indicatorName\": \"近期存在负面舆情\", \"indicatorValue\": null}], \"labelCode\": \"CMN00013\", \"labelName\": \"存在负面舆情\"}, {\"inicators\": [{\"props\": [{\"time\": \"2019-07-12\", \"type\": \"工商风险\", \"detail\": \"未依照《企业信息公示暂行条例》第八条规定的期限公示年度报告的\", \"reason\": \"企业已列入经营异常名录\", \"docType\": null}, {\"time\": \"2018-09-19\", \"type\": \"工商风险\", \"detail\": \"通过登记的住所或者经营场所无法联系的\", \"reason\": \"企业已列入经营异常名录\", \"docType\": null}], \"indicatorCode\": \"CMN00019\", \"indicatorName\": \"企业近期经营异常\", \"indicatorValue\": null}], \"labelCode\": \"CMN00019\", \"labelName\": \"经营异常\"}, {\"inicators\": [{\"props\": [{\"time\": \"2016-01-18\", \"type\": \"司法涉诉\", \"detail\": \"（2016）津0116功执字第80079号|其他有履行能力而拒不履行生效法律文书确定义务\", \"reason\": \"杨彬（直接和间接持股至少 30% 的股东）已列入失信被执行名单\", \"docType\": null}], \"indicatorCode\": \"CMN00034\", \"indicatorName\": \"法定代表人或者重要股东失信被执行\", \"indicatorValue\": null}], \"labelCode\": \"CMN00034\", \"labelName\": \"法定代表人/重要股东失信被执行\"}, {\"inicators\": [{\"props\": [{\"time\": \"2018-01-01\", \"type\": \"司法涉诉\", \"detail\": \"1|天津市河西区人民法院（2018）津0103民初11065号裁定书中提到：天津百利恒信资产管理有限公司涉嫌非法吸收公众存款犯罪，已被天津市公安局河西分局刑事立案侦查\", \"reason\": \"司法涉诉疑似非法集资\", \"docType\": null}], \"indicatorCode\": \"CMN00049\", \"indicatorName\": \"涉嫌非法集资或传销-司法诉讼\", \"indicatorValue\": null}], \"labelCode\": \"CMN00049\", \"labelName\": \"非法集资/传销涉诉\"}]";
        //加签
        String sign = signByPrivateKey(bizData, PRIVATE_KEY);
        //验签
        boolean b = verifySignByPublicKey(bizData, PUBLIC_KEY, sign);
        System.out.println(b?"验签通过":"验签不通过");
        System.out.println("--------------------------------------------------------------------------------------------------");
        //生成16位随机密钥
        String aesRandomKey = getAESRandomKey();
        //使用16随机密钥加密数据
        String AESData = encryptByAES(bizData, aesRandomKey);
        //使用公钥对16位随机密钥加密
        String randomKey = encryptByRSA(aesRandomKey,PUBLIC_KEY);
        //使用私钥对16位随机密钥解密
        String  aesRandomKey02= decryptByRSA(randomKey,PRIVATE_KEY);
        //使用16位随机密钥解密业务数据
        String bizData2 = decryptByAESPadding(AESData, aesRandomKey02);
        System.out.println(bizData2.equals(bizData)?"解密成功":"解密失败");
    }




    public static PrivateKey getPrivaKeyFromString(String base64String) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bt = Base64.decodeBase64(base64String);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bt);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        PrivateKey privateKey=keyFactory.generatePrivate(privateKeySpec);

        return privateKey;
    }

    /**
     * 私钥加签
     * @param inputStr 业务数据
     * @param privateKey 私钥
     * @return 返回签名值
     * @throws Exception
     */
    public static String signByPrivateKey(String inputStr,String privateKey) throws Exception{
        return Base64.encodeBase64String(signByPrivateKey(inputStr.getBytes(),getPrivaKeyFromString(privateKey)));
    }

    private static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception{
        Signature sig=Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] ret = sig.sign();
        return ret;
    }


    public static PublicKey getPublicKeyFromString(String base64String) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        byte[] bt = Base64.decodeBase64(base64String);

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bt);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        PublicKey publicKey=keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    /**
     * 验签
     * @param inputStr 业务数据
     * @param publicKey 公钥
     * @param base64SignStr 签名
     * @return 验签结果
     * @throws Exception
     */
    public static boolean verifySignByPublicKey(String inputStr,String publicKey,String base64SignStr) throws Exception{
        return  verifyByPublicKey(inputStr.getBytes(),getPublicKeyFromString(publicKey),Base64.decodeBase64(base64SignStr));
    }



    public static boolean verifyByPublicKey(byte[] data,PublicKey publicKey,byte[] signature) throws Exception{
        Signature sig=Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        boolean ret= sig.verify(signature);
        return ret;
    }



    /**
     * 生成16位AES随机密钥
     * @return
     */
     public static String getAESRandomKey(){
         Random random = new Random();
         Long longValue = random.nextLong();
         return String.format("%016x",longValue);

     }

    /**
     * 使用16随机密钥加密数据
     * @param inputStr 要加密的数据
     * @param password 16位随机密钥
     * @return 加密后的数据
     * @throws Exception
     */
     public static String encryptByAES(String inputStr,String password) throws Exception{
         byte[] byteData=inputStr.getBytes();
         byte[] bytePassword= password.getBytes();
         return Base64.encodeBase64String(encryptByAES(byteData,bytePassword));
     }

     public static byte[] encryptByAES(byte[] data, byte[] pwd)throws Exception{
         Cipher cipher=Cipher.getInstance("AES");
         SecretKeySpec keySpec=new SecretKeySpec(pwd,"AES");
         cipher.init(Cipher.ENCRYPT_MODE,keySpec);
         byte[] ret= cipher.doFinal(data);
         return ret;
     }

    /**
     * 使用公钥对 AES 随机密钥进行加密生成 randomKey
     */
    public static String encryptByRSA(String inputStr,String publicKey) throws Exception{
        PublicKey key=getPublicKeyFromString(publicKey);
        return Base64.encodeBase64String(encryptByRSA(inputStr.getBytes(),key));
    }

    /**
    * 加密
     * @param input
     * @param key
     * @return
     * @throws Exception
    */
    public static byte[] encryptByRSA(byte[] input,Key key) throws  Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] output =cipher.doFinal(input);
        return output;
    }

    /**
     * 使用私钥对加密后的16位随机密钥解密
     * @param inputStr 加密后的16位随机密钥
     * @param privateKey 私钥
     * @return 16位随机密钥
     * @throws Exception
     */
    public static String decryptByRSA(String inputStr,String privateKey) throws Exception{
        PrivateKey key= getPrivaKeyFromString(privateKey);
        return new String(decryptByRSA(Base64.decodeBase64(inputStr),key));
    }

    /**
     * 解密
     * @param input
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByRSA(byte[] input,Key key) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] output =cipher.doFinal(input);
        return output;
    }




    /**
     * 使用 AES 密钥对加密的业务字段 bizData 进行解密得到业务数据
     * @param inputStr 加密后的业务数据
     * @param password 16位随机密钥
     * @return 解密后的业务数据
     * @throws Exception
     */
    public static String decryptByAESPadding(String inputStr,String password) throws Exception{
        byte[] byteData = Base64.decodeBase64(inputStr);
        byte[] bytePassword = password.getBytes();
        return new String(decryptByAesPKCS5(byteData,bytePassword));
    }

    public static byte[] decryptByAesPKCS5(byte[] data,byte[] pwd) throws Exception{
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec= new SecretKeySpec(pwd,"AES");
        cipher.init(Cipher.DECRYPT_MODE,keySpec);
        byte[] ret= cipher.doFinal(data);
        return ret;
    }
}
