package net.xdclass.service;

import net.xdclass.model.AddressDO;
import net.xdclass.request.AddressAddRequest;
import net.xdclass.vo.AddressVO;

import java.util.List;

public interface AddressService {

    AddressVO detail(long id);

    /**
     * 新增收货地址
     * @param addressAddRequest
     */
    void add(AddressAddRequest addressAddRequest);

    /**
     * 根据id删除地址
     * @param addressId
     * @return
     */
    int del(int addressId);

    /**
     * 查找用户全部收货地址
     * @return
     */
    List<AddressVO> listUserAllAddress();
}
