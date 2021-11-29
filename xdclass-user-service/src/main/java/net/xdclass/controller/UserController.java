package net.xdclass.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.request.UserLoginRequest;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.FileService;
import net.xdclass.service.UserService;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;


    /**
     * 文件上传流程
     * <p>
     * * 先上传文件，返回url地址，再和普通表单一并提交（推荐这种,更加灵活，失败率低）
     * * 文件和普通表单一并提交（设计流程比较多，容易超时和失败）
     *
     * @return net.xdclass.utils.JsonData
     * @Author viy
     * @Description 用户头像上传接口
     * @Date 14:24 2021/11/26
     * @Param [file] RequestPart 默认大小为1M SpringBoot最大文件上传为1M
     * <p>
     * * @requestPart注解 接收文件以及其他更为复杂的数据类型
     * * 比如 XXX(@RequestPart("file") MultipartFile file,  @RequestPart("userVO") UserVO userVO) 复杂协议
     **/
    @ApiOperation("用户头像上传")
    @PostMapping(value = "upload")
    public JsonData uploadUserImg(@ApiParam(value = "文件上传", required = true) @RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserImg(file);
        return result != null ?
                JsonData.buildSuccess(result) :
                JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }


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

    /**
     * @return net.xdclass.utils.JsonData
     * @Author viy
     * @Description 用户登录接口
     * @Date 17:28 2021/11/26
     * @Param [userLoginRequest]
     **/
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JsonData login(@ApiParam("用户登录对象") @RequestBody UserLoginRequest userLoginRequest) {
        JsonData login = userService.login(userLoginRequest);
        return login;
    }

    //    刷新token的方案
//    @PostMapping("refresh_token")
//    public JsonData getRefreshToken(Map<String,Object> param){
//
//        //先去redis,找refresh_token是否存在
//        //refresh_token存在，解密accessToken
//        //重新调用JWTUtil.geneJsonWebToken() 生成accessToken
//        //重新生成refresh_token，并存储redis，设置30天过期时间
//        //返回给前端
//        return null;
//    }

}

