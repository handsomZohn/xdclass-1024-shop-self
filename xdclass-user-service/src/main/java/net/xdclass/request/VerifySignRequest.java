package net.xdclass.request;

import lombok.Data;

/**
 * @ClassName VerifySignRequest
 * @Description TODO
 * @Date 2022/3/7 10:52
 * @Version 1.0
 **/
@Data
public class VerifySignRequest {

    /**
     * 签名
     */
    private String sign;

    /**
     * 业务数据
     */
    private String bizData;
}
