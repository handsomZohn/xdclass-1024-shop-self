package net.xdclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.interceptor.LoginInterceptor;
import net.xdclass.mapper.UserMapper;
import net.xdclass.model.LoginUser;
import net.xdclass.model.UserDO;
import net.xdclass.request.UserLoginRequest;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.NotifyService;
import net.xdclass.service.UserService;
import net.xdclass.utils.CommonUtil;
import net.xdclass.utils.JWTUtil;
import net.xdclass.utils.JsonData;
import net.xdclass.vo.UserVO;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * * 邮箱验证码验证
     * * 密码加密
     * * 账号唯一性检查
     * * 插入数据库
     * * 新注册用户福利发放(TODO)
     *
     * @param registerRequest
     * @return
     */
    @Override
    public JsonData register(UserRegisterRequest registerRequest) {
        boolean checkCode = false;
        // 检验邮箱验证码
        if (StringUtils.isNotBlank(registerRequest.getMail())) {
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, registerRequest.getMail(), registerRequest.getCode());
        }

        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(registerRequest, userDO);

        userDO.setCreateTime(new Date());
        userDO.setSlogan("人生需要动态规划，学习需要贪心算法");

        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        // 密码加盐处理
        String cryptPwd = Md5Crypt.md5Crypt(registerRequest.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);

        if (!checkUnique(userDO.getMail())) {
            int rows = userMapper.insert(userDO);
            log.info("rows:{},注册成功:{}", rows, userDO.toString());

            // TODO 新用户注册成功，初始化信息，发放福利等
            userRegisterInitTask(userDO);
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    /**
     * @return net.xdclass.utils.JsonData
     * @Author viy
     * @Description 用户登录方法  登录之后 生成token放入缓存 供其他服务做身份认证使用
     * @Date 17:44 2021/11/26
     * @Param [userLoginRequest]
     * 1、根据Mail去找有没这记录
     * 2、有的话，则用秘钥+用户传递的明文密码，进行加密，再和数据库的密文进行匹配
     **/
    @Override
    public JsonData login(UserLoginRequest userLoginRequest) {

        // 根据mail查询
        List<UserDO> list = userMapper.selectList(
                new QueryWrapper<UserDO>().eq("mail", userLoginRequest.getMail()));

        if (list != null && list.size() == 1) {

            UserDO userDO = list.get(0);
            String cryptPwd = Md5Crypt.md5Crypt(userLoginRequest.getPwd().getBytes(), userDO.getSecret());
            if (cryptPwd.equals(userDO.getPwd())) {
                LoginUser loginUser = new LoginUser();
                BeanUtils.copyProperties(userDO, loginUser);
                String token = JWTUtil.geneJsonWebToken(loginUser);

                // accessToken
                // accessToken的过期时间
                // UUID生成一个token
                // String refreshToken = CommonUtil.generateUUID();
                // redisTemplate.opsForValue().set(refreshToken,"1",1000*60*60*24*30);

                return JsonData.buildSuccess(token);
            }

            // 密码错误
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
        } else {
            // 未注册
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }

    }

    /**
     * @return net.xdclass.vo.UserVO
     * @Author viy
     * @Description 查询用户详情
     * @Date 16:49 2021/11/30
     * @Param []
     **/
    @Override
    public UserVO findUserDetail() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", loginUser.getId()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    /**
     * 校验用户账号唯一 已存在返回true
     *
     * @param mail
     * @return
     */
    private boolean checkUnique(String mail) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("mail", mail);
        wrapper.select("mail");
        List<UserDO> userDOS = userMapper.selectList(wrapper);
        return userDOS != null && userDOS.size() > 0;
    }


    /**
     * TODO 用户注册，初始化福利信息
     *
     * @param userDO
     */
    private void userRegisterInitTask(UserDO userDO) {

    }
}
