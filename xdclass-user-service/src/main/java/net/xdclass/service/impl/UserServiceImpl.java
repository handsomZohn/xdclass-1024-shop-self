package net.xdclass.service.impl;

import net.xdclass.request.UserLoginRequest;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.UserService;
import net.xdclass.utils.JsonData;
import net.xdclass.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {


    @Override
    @Transactional
    public JsonData register(UserRegisterRequest registerRequest) {
        return null;
    }

    @Override
    public JsonData login(UserLoginRequest userLoginRequest) {
        return null;
    }

    @Override
    public UserVO findUserDetail() {
        return null;
    }
}
