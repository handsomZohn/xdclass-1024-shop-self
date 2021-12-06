package net.xdclass.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.model.LoginUser;
import net.xdclass.utils.CommonUtil;
import net.xdclass.utils.JWTUtil;
import net.xdclass.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    /**
     * @return boolean
     * @Author viy
     * @Description 进入目标controller方法之前
     * @Date 14:19 2021/11/30
     * @Param [request, response, handler]
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 多种方式获取一下token
        String accessToken = request.getHeader("token");
        if (accessToken == null) {
            accessToken = request.getParameter("token");
        }

        if (StringUtils.isNotBlank(accessToken)) {
            Claims claims = JWTUtil.checkJWT(accessToken);
            if (claims == null) {
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }

            Long userId = Long.valueOf(claims.get("id").toString());
            String headImg = (String) claims.get("head_img");
            String name = (String) claims.get("name");
            String mail = (String) claims.get("mail");

            LoginUser loginUser = LoginUser.builder()
                    .headImg(headImg)
                    .name(name)
                    .id(userId)
                    .mail(mail)
                    .build();


            // 通过attribute传递用户信息
            // request.setAttribute("loginUser", loginUser);
            // request.getAttribute("loginUser");


            // 通过threadlocal传递用户登录信息
            threadLocal.set(loginUser);
            // LoginUser loginUser1 = threadLocal.get();
            return true;
        }

        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
