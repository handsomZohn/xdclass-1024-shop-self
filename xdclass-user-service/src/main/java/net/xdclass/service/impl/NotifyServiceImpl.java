package net.xdclass.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.component.MailService;
import net.xdclass.constant.CacheKey;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.NotifyService;
import net.xdclass.utils.CheckUtil;
import net.xdclass.utils.CommonUtil;
import net.xdclass.utils.JsonData;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 验证码的标题
     */
    private static final String SUBJECT = "张杨专属验证码";

    /**
     * 验证码的内容  可以做成配置化的模板 比如，注册的，找回密码的，修改密码的，等等
     */
    private static final String CONTENT = "您的验证码是%s,有效时间是10分钟,不要告诉任何人,除非忍不住~~";

    /**
     * 验证码10分钟有效
     */
    private static final int CODE_EXPIRED = 60 * 1000 * 10;

    /**
     * 前置：判断是否重复发送
     * 1、存储验证码到缓存
     * 2、发送邮箱验证码
     * 后置：存储发送记录
     *
     * @param sendCodeEnum
     * @param to
     * @return
     */
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {

        // 验证码：类型：发送地址
        // 发送一个验证码，验证码的存在redis中的key code:USER_REGISTER:18310834045@163.com
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        // 发送一个验证码，验证码的value 054024_1632626122149
        // 让验证码拼上时间戳  验证码_时间戳  切割之后拿当时发送时的时间戳和当前时间戳作对比，判断是否频繁发送
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        //如果不为空，则判断是否60秒内重复发送
        if (StringUtils.isNotBlank(cacheValue)) {
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            //当前时间戳-验证码发送时间戳，如果小于60秒，则不给重复发送
            if (CommonUtil.getCurrentTimestamp() - ttl < 1000 * 60) {
                log.info("重复发送验证码,时间间隔:{} 秒", (CommonUtil.getCurrentTimestamp() - ttl) / 1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }
        //拼接验证码 2322_324243232424324
        String code = CommonUtil.getRandomCode(6);
        String value = code + "_" + CommonUtil.getCurrentTimestamp();
        redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRED, TimeUnit.MILLISECONDS);

        // 也可以放入对应的队列里面去
        // 确保消息可靠投递就可以了 避免分布式事务

        // 检查接收人邮箱地址格式或者手机号码格式并发送
        if (CheckUtil.isEmail(to)) {
            //邮箱验证码
            mailService.sendMail(to, SUBJECT, String.format(CONTENT, code));
            return JsonData.buildSuccess();
        } else if (CheckUtil.isPhone(to)) {
            //短信验证码
        }
        // 接收号码不合格
        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    /**
     * 校验验证码
     *
     * @param sendCodeEnum
     * @param to
     * @param code
     * @return
     */
    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        // 验证码的缓存key
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        // 根据key 去获取验证码的值
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        // 拿到缓存的值，截取出验证码 作比较
        if (StringUtils.isNotBlank(cacheValue)) {
            String cacheCode = cacheValue.split("-")[0];
            if (cacheCode.equals(code)) {
                // 删除验证码
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }

    // 考虑到文件存储 是什么类型的，公司的文件存储是那种类型的,选用什么样的存储架构来存储，如何才能最省成本；
    // 日访问频率 中等频率 高频率等
}
