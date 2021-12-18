package net.xdclass.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.request.AddressAddRequest;
import net.xdclass.service.AddressService;
import net.xdclass.utils.JsonData;
import net.xdclass.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址控制类
 */
@Slf4j
@Api(tags = "用户地址模块")
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/find/{address_id}")
    public Object detail(@PathVariable("address_id") long address_id) {
        AddressVO detail = addressService.detail(address_id);
        // int i = 0;

        // 模拟抛出一个全局异常
        // int j = 1 / 0;

        // 模拟抛出一个业务异常
//        if (address_id == 1) {
//            throw new BizException(900, "测试自定义业务异常");
//        }
        detail = new AddressVO();
        detail.setDetailAddress("信息路48号-北京体育大学-五道口职业技术学院------");
        log.info("地址对象内容为:{}", detail);
        return detail;
    }


    @ApiOperation("新增收货地址")
    @PostMapping("add")
    public JsonData add(@ApiParam("地址对象") @RequestBody AddressAddRequest addressAddRequest) {
        addressService.add(addressAddRequest);
        return JsonData.buildSuccess();
    }


    /**
     * 删除指定收货地址
     *
     * @param addressId
     * @return
     */
    @ApiOperation("删除指定收货地址")
    @DeleteMapping("/del/{address_id}")
    public JsonData del(
            @ApiParam(value = "地址id", required = true)
            @PathVariable("address_id") int addressId) {
        int rows = addressService.del(addressId);
        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.ADDRESS_DEL_FAIL);
    }

    /**
     * 查询用户的全部收货地址
     *
     * @return
     */
    @ApiOperation("查询用户的全部收货地址")
    @GetMapping("/list")
    public JsonData findUserAllAddress() {
        List<AddressVO> list = addressService.listUserAllAddress();
        return JsonData.buildSuccess(list);
    }


    /*主要是因为开发人员在对数据进行增、删、改、查询时对客户端请求的数据过分相信，而遗漏了权限的判定
     */
}

