package net.xdclass.service.impl;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.config.AppConfig;
import net.xdclass.model.UserFamilyMemberInfo;
import net.xdclass.model.UserInfo;
import net.xdclass.service.SignAndEncryptService;
import net.xdclass.utils.signandencrypt.RSAUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SignAndEncryptServiceImpl
 * @Description TODO
 * @Date 2022/3/5 14:40
 * @Version 1.0
 **/
@Service
@Slf4j
public class SignAndEncryptServiceImpl implements SignAndEncryptService {


    @Autowired(required = false)
    private RestTemplate restTemplate;


    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;

    /**
     * 发起服务的调用
     */
    @Override
    public void startProcess() {
        String s = this.dataBuild();

        /**
         * 加签
         */
        String sign = null;
        try {
            sign = RSAUtils.sign(s.getBytes(), privateKey);
        } catch (Exception e) {
            log.error("加签失败");
            e.printStackTrace();
        }

        /**
         * 构建请求
         */
        String url = "http://localhost:9001/api/sign/v2/verify";
        HashMap<String, String> paramMap = new HashMap<>(2);
        paramMap.put("sign", sign);
        paramMap.put("bizData", s);

        String s1 = restTemplate.postForObject(url, paramMap, String.class);
        System.out.println(s1);
    }


    public String dataBuild() {

        UserInfo userInfo = new UserInfo();
        userInfo.setName("张娃");
        userInfo.setAge("1");
        userInfo.setSex("男");
        UserFamilyMemberInfo userFamilyMemberInfo = new UserFamilyMemberInfo();
        userFamilyMemberInfo.setName("张爸");
        userFamilyMemberInfo.setAge("23");
        userFamilyMemberInfo.setRelationship("父亲");
        UserFamilyMemberInfo userFamilyMemberInfo02 = new UserFamilyMemberInfo();
        userFamilyMemberInfo02.setName("张妈");
        userFamilyMemberInfo02.setAge("22");
        userFamilyMemberInfo02.setRelationship("母亲");
        List<UserFamilyMemberInfo> userFamilyMemberInfos = new ArrayList<>();
        userFamilyMemberInfos.add(userFamilyMemberInfo02);
        userFamilyMemberInfos.add(userFamilyMemberInfo);
        userInfo.setUserFamilyMemberInfos(userFamilyMemberInfos);
        Object o = JSONArray.toJSON(userInfo);
        String string = o.toString();
        return string;
    }


    @Test
    public void dataBuildTest() {
        String s = this.dataBuild();
        System.out.println(s);
    }
}
