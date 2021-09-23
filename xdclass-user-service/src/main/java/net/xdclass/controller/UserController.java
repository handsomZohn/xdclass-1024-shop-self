package net.xdclass.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.UserService;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userDO")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public JsonData register(@ApiParam("用户注册对象") @RequestBody UserRegisterRequest registerRequest) {

        JsonData jsonData = userService.register(registerRequest);
        return jsonData;
    }

}
