package club.zhangliyuanblog.interceptor;

import club.zhangliyuanblog.exception.NoTokenException;
import club.zhangliyuanblog.util.JWTUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 21:23
 * 拦截器
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的令牌
        String token = request.getHeader("token");

        if (StrUtil.hasBlank(token)) {
            throw new NoTokenException();
        }

        JWTUtils.verify(token);
        // 放行请求
        return true;
    }
}
