package net.xdclass.utils.signandencrypt.jialiang;

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
        //String bizData = "{\"bizData\":{\"baseInfo\":{\"agentOrgCode\":\"011\",\"customerType\":\"111\",\"partnerCode\":\"011\",\"partnerProjectCode\":\"011\"},\"FILE_NUM\":\"2\",\"materials\":[{\"materialName\":\"SFZFM_202204060001.png\",\"materialURL\":\"111\",\"serialNum\":\"01\",\"materialType\":\"111\"},{\"materialName\":\"SFZZM_202204060001.png\",\"materialURL\":\"111\",\"serialNum\":\"01\",\"materialType\":\"111\"}],\"personInfo\":{\"education\":\"111\",\"residentialProvince\":\"1111\",\"identityStartDate\":\"2022\\/3\\/23\",\"nation\":\"1\",\"customerSex\":\"2\",\"identityNo\":\"410381199912219016\",\"post\":\"1\",\"tel\":\"15225578998\",\"permanentAddress\":\"2\",\"profession\":\"3\",\"degree\":\"4\",\"spouseCompany\":\"5\",\"workOrganization\":\"1\",\"customerName\":\"1212\",\"livingConditions\":\"12\",\"spouseIdentityNo\":\"410381199904219015\",\"permanentAddressDetail\":\"213\",\"trade\":\"123\",\"identityEndDate\":\"2022\\/3\\/31\",\"residentialAddress\":\"123\",\"spouseTel\":\"15224452342\",\"livingFitRegistry\":\"1\",\"spouseName\":\"1\",\"workOrganizationAddr\":\"123\",\"maritalStatus\":\"123\",\"applicantIncome\":\"10000\"}}}";
        String bizData = "{\"baseInfo\":{\"agentOrgCode\":\"011\",\"customerType\":\"01\",\"partnerCode\":\"011\",\"partnerProjectCode\":\"011\"},\"FILE_NUM\":2,\"programmeInfo\":{\"poundageScale\":\"0.2\",\"leaseCorpus\":\"100000\",\"poundage\":\"200\",\"seasonalRepaymentEnd\":\"09\",\"customerInterest\":\"5\",\"seasonalRepaymentStart\":\"06\",\"leaseTerms\":\"12\",\"productCode\":\"p001\",\"estimateStartingRentDate\":\"2022-04-08\",\"discountInterest\":[{\"leaseTime\":\"1\",\"discountInterestAmount\":\"500\"},{\"leaseTime\":\"1\",\"discountInterestAmount\":\"500\"}],\"depositScale\":\"5\",\"deposit\":\"5000\",\"profit\":[{\"leaseTime\":\"1\",\"profitAmount\":\"200\"},{\"leaseTime\":\"1\",\"profitAmount\":\"200\"}],\"downpayment\":\"10000\"},\"materials\":[{\"materialName\":\"SFZFM_202204060001.png\",\"materialURL\":\"C\\/文档\",\"serialNum\":\"01\",\"materialType\":\"合同材料\"},{\"materialName\":\"SFZFM_202204060001.png\",\"materialURL\":\"C\\/文档\",\"serialNum\":\"01\",\"materialType\":\"合同材料\"}],\"leaseholdInfo\":[{\"affiliatedCompany\":\"挂号单位名称\",\"registrar\":\"登记机关单位1\",\"affiliatedRepresentative\":\"张三\",\"devicePrice\":\"100000\",\"affiliated\":\"1\",\"deviceCode\":\"租赁物设备编号\",\"leaseholdId\":\"asdfghjhgfd\",\"mortgage\":\"1\",\"deviceSeries\":\"租赁物车系\",\"deviceModel\":\"租赁物型号\",\"deviceBrand\":\"租赁物品牌\",\"remarks\":\"无\",\"powerType\":\"1\",\"deviceEstimatePrice\":\"100000\"}],\"personInfo\":{\"education\":\"研究生\",\"residentialProvince\":\"北京市朝阳区\",\"identityStartDate\":\"2022\\/3\\/23\",\"nation\":\"汉\",\"customerSex\":\"1\",\"identityNo\":\"410381199912219016\",\"post\":\"中层人员\",\"tel\":\"15225578998\",\"permanentAddress\":\"北京市朝阳区\",\"profession\":\"专业技术人员\",\"degree\":\"01\",\"spouseCompany\":\"中国移动\",\"workOrganization\":\"中国移动\",\"customerName\":\"普惠租赁\",\"livingConditions\":\"自置\",\"spouseIdentityNo\":\"410381199904219015\",\"permanentAddressDetail\":\"北京市朝阳区\",\"trade\":\"金融\",\"identityEndDate\":\"2022\\/3\\/31\",\"residentialAddress\":\"北京市朝阳区\",\"spouseTel\":\"15224452342\",\"livingFitRegistry\":\"是\",\"spouseName\":\"莉莉\",\"workOrganizationAddr\":\"北京朝阳\",\"maritalStatus\":\"单身\",\"applicantIncome\":\"10000\"}}";
        //加签
        String sign = signByPrivateKey(bizData, PRIVATE_KEY);
        System.out.println(sign);
        //验签
        boolean b = verifySignByPublicKey(bizData, PUBLIC_KEY, sign);
        System.out.println(b?"验签通过":"验签不通过");
        System.out.println("--------------------------------------------------------------------------------------------------");
        //生成16位随机密钥
        String aesRandomKey = getAESRandomKey();
        //使用16随机密钥加密数据
        String AESData = encryptByAES(bizData, aesRandomKey);
        System.out.println("加密数据=="+ AESData);
        //使用公钥对16位随机密钥加密
        String randomKey = encryptByRSA(aesRandomKey,PUBLIC_KEY);
        System.out.println("加密后的密钥="+randomKey);
        //使用私钥对16位随机密钥解密
        String  aesRandomKey02= decryptByRSA("6xjVmLvGh+kjgl6U0Z7VjDCl0s6bpE2abkGrfr4zuoyRYpvKINkKTDY8VtdQb700FwDy1Z3CYIr8148Y9TH2MA==",PRIVATE_KEY);
        //使用16位随机密钥解密业务数据
        String bizData2 = decryptByAESPadding("enpgel9Yb0CB9tqF4R7C5jy5nAsr2EZRtgZEI3A18g5vNJgzpQSPkQWTqKFjZMiEYby9YCc07SEyoDriAZ1VoA==", aesRandomKey02);
        System.out.println("解密数据 ====" + bizData2);
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
        return  verifyByPublicKey(inputStr.getBytes(StandardCharsets.UTF_8),getPublicKeyFromString(publicKey),Base64.decodeBase64(base64SignStr));
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
