package net.xdclass.utils.signandencrypt02;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class JsonToMapTest01 {
    public static void main(String[] args) {

        String str = "{\"randomKey\":\"PWH6Og1VPwLqVW4mw2YGoKHtSynYTQ9PSKURFaS3fvhQcSuMHOAnglIBqPeibgmvzC6T8jn2x/gNVZvGVRThEg==\",\"sign\":\"FbEQQAs14NYmvuUxT/CNKzRYnD87Dl054ffhB5XemH0SUoKlb/LtJgAdS9UJe1GBp4wxfFwk+rY8kfh97pDTLA==\",\"bizData\":\"7cWN8Z/BWhmTRHkPeeCxSZzurXolss7aEb9YF1/Xl695A6hxeXp8Vg7g7/q2IXuw1fNHOP5WBcclw3AmPzNFfoNB2WKpbjZmyEfjcK0Xv27/cPLluklF8ylxyFFMEWs3opsGfCoeomZLyLzZ1O1eQimfS+oT5PmUOaxEz6jP1k2uFHAYWvqLF7gs2p/lLSOzsziAg92zaqBUOtF/Urweu+phkv8g7dt2mOqcG9ZoLgWOGU6rit+NQPblUmTsppB+omKSEv39NJDV96OLTEFI3H7G3toY4+yipYNnXJSeYVHg3Xuihlelg7M5RoiEBUVgdUX3kCL0gMMWSlRzZu8JcrOCb0j2qjyTs1ORszjaIJMesPN4JKWOxu3ZNoj6IJJlaRruXdKcEo/yHbr7YHG4kbC0yrdRnvJXF2Bpi2PrEuYF29wpW9emInj4vht23eNuh9dos7qXmPKYrj9KPUuLCZSrWW63J6D+HrTBIoGfcmmdJAs6JBPUlejnbF8TQFfgy5ijS71AdRt6zV/2UJQc0z5Doj/g4485WFnnxQX9j+T3VFYntH+9zTFb8jKiPgclwMsUT8A+fWb3h1Dng9OBgnXUbQuNC893m7+vju70eTvjh2rkliliVCl4x42RV3a0ELHC0S6PHXkmZxB1KuA1m/mgllJPZmarlLZy8zZH40QLSsu22xCPUXcXohDif4g8qyaY8g7GTsF1Padxet2QDZbm49UvaOBODlF22a17NimZgFUXToOHfZaV3HikQqrqitjtYoNsK8R2aFwBavnf5fL9BbH4tEf838VU5BSUcUupMuqlAUslkrf5SOm77O4x6L+eI9s4zNnE+ASiNJ1MveKDSREQF662hFzkFChuEseE8U1E9EgyUleYv7bdcsyLQH9r8xfrMlplUOuu2xgl4FBTiGMIjbOu5sL1BFX5dXEuz1/tuhqQ39OK8bj2zqN7o26cPl25+IzuoOMqG1o1u961zwS6SIqVcqU1Nw9f+gFosINmGWHxm9OvqSzY69oa40AcNFQZHoxK7pxNapRfiQjxBCBeN4omvoZxVFJnd2fXfXcHvC6trP7FQIYEC9mF7R4/03nINBfJlCR71GLPuKC+T6k/kQsnRS3g5R8R2uHFPGFV7vwn/UraOkPrzvNCsmRtDpYpnEYIDpP7BYBIuHURsKphwgFrSoh+bT1dxYAjsUmxeyL0o+XM5pbEfwx9mYBVF06Dh32Wldx4pEKq6lVq3pwxorEP6LeU5OT5l8BjPQBHsCxEuCX1cDMeM/VmYTNm1m+dabiQXmGLKvtLCF2XP+EPGWVU/q25+2kTDMRZqyCJ+Db/ZSnzEYRNrTaIS6WOMDrgowWBa9oTy2cLdB+87r/6hmxfH7P8vX/cSLNnWHu9+gPGKu4b7kfunDhq9fmH3g0C8VGBjqhJT6NNkwo9qaDhpdykjPfO3ehlRtUxw664w79A65wk1Ju5L+EN\",\"reqTime\":\"2022-03-25\"}";
        //第一种方式
        Map maps = (Map) JSON.parse(str);
        System.out.println("这个是用JSON类来解析JSON字符串!!!");
        for (Object map : maps.entrySet()) {
            System.out.println(((Map.Entry) map).getKey() + "     " + ((Map.Entry) map).getValue());
        }


        byte[] bytes = {123, 34, 114, 97, 110, 100, 111, 109, 75, 101, 121, 34, 58, 34, 80, 87, 72, 54, 79, 103, 49, 86, 80, 119, 76, 113, 86, 87, 52, 109, 119, 50, 89, 71, 111, 75, 72, 116, 83, 121, 110, 89, 84, 81, 57, 80, 83, 75, 85, 82, 70, 97, 83, 51, 102, 118, 104, 81, 99, 83, 117, 77, 72, 79, 65, 110, 103, 108, 73, 66, 113, 80, 101, 105, 98, 103, 109, 118, 122, 67, 54, 84, 56, 106, 110, 50, 120, 47, 103, 78, 86, 90, 118, 71, 86, 82, 84, 104, 69, 103};
        String s = new String(bytes);
        System.out.println(s);

        byte[] bytes02 = {123};
        String s02 = new String(bytes02);
        System.out.println(s02);
    }


}
