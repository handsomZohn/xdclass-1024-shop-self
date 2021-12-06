package net.xdclass.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AddressAddRequest
 * @Description 新增地址实体类
 * @Date 2021/11/30 17:04
 * @Version 1.0
 **/
@Data
@ApiModel(value = "地址对象", description = "新增收获地址对象")
public class AddressAddRequest {

    @ApiModelProperty(value = "是否默认收货地址0否1是", example = "0")
    @JsonProperty("default_status")
    private Integer defaultStatus;

    @ApiModelProperty(value = "收发货人姓名", example = "张毅峰")
    @JsonProperty("receive_name")
    private String receiveName;

    @ApiModelProperty(value = "收货人电话", example = "18310834046")
    private String phone;

    /**
     * 省/直辖市
     */
    @ApiModelProperty(value = "省/直辖市", example = "广东省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "城市", example = "广州市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区", example = "天河区")
    private String region;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", example = "运营中心-老王隔壁1号")
    @JsonProperty("detail_address")
    private String detailAddress;
}
