package net.xdclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.AddressStatusEnum;
import net.xdclass.interceptor.LoginInterceptor;
import net.xdclass.mapper.AddressMapper;
import net.xdclass.model.AddressDO;
import net.xdclass.model.LoginUser;
import net.xdclass.request.AddressAddRequest;
import net.xdclass.service.AddressService;
import net.xdclass.vo.AddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * @return net.xdclass.model.AddressDO
     * @Author viy
     * @Description 地址id获取地址详情
     * @Date 17:22 2021/11/30
     * @Param [id]
     **/
    @Override
    public AddressVO detail(long id) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        // 防止水平越权攻击，拼接上用户的id
        AddressDO id1 = addressMapper.selectOne(new QueryWrapper<AddressDO>()
                .eq("id", id)
                .eq("user_id", loginUser.getId())
        );
        // 查询出来的是一个DO DO是不能直接返回到页面的，所以要弄一个VO，把VO返回到页面

        if (id1 == null) {
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(id1, addressVO);
        return addressVO;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void add(AddressAddRequest addressAddRequest) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        addressDO.setUserId(loginUser.getId());

        BeanUtils.copyProperties(addressAddRequest, addressDO);


        //是否有默认收货地址
        if (addressDO.getDefaultStatus() == AddressStatusEnum.DEFAULT_STATUS.getStatus()) {
            //查找数据库是否有默认地址
            AddressDO defaultAddressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>()
                    .eq("user_id", loginUser.getId())
                    .eq("default_status", AddressStatusEnum.DEFAULT_STATUS.getStatus()));

            if (defaultAddressDO != null) {
                //修改为非默认收货地址
                defaultAddressDO.setDefaultStatus(AddressStatusEnum.COMMON_STATUS.getStatus());
                addressMapper.update(defaultAddressDO, new QueryWrapper<AddressDO>().eq("id", defaultAddressDO.getId()));
            }
        }

        int rows = addressMapper.insert(addressDO);

        log.info("新增收货地址:rows={},data={}", rows, addressDO);
    }

    /**
     * @return int
     * @Author viy
     * @Description 根据id删除某条地址信息  要删除的是当前登录人的
     * @Date 16:36 2021/12/2
     * @Param [addressId]
     **/
    @Override
    public int del(int addressId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        int rows = addressMapper.delete(new QueryWrapper<AddressDO>().eq("id", addressId).eq("user_id", loginUser.getId()));
        // int rows = addressMapper.deleteById(addressId);
        return rows;
    }

    /**
     * @return java.util.List<net.xdclass.vo.AddressVO>
     * @Author viy
     * @Description 查找当前用户所有的收货地址
     * @Date 16:57 2021/12/2
     * @Param []
     **/
    @Override
    public List<AddressVO> listUserAllAddress() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        List<AddressDO> addressDOList = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()));
        List<AddressVO> addressVOList = addressDOList.parallelStream().map(x -> {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(x, addressVO);
            return addressVO;
        }).collect(Collectors.toList());
        return addressVOList;
    }
}
