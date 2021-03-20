package club.zhangliyuanblog.util;


import club.zhangliyuanblog.properties.SystemProperties;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @author liyuan.zhang
 * @date 2021/3/2 14:29
 */
@Component
public class JWTUtils {

    private static SystemProperties systemProperties;

    @Autowired
    public void init(SystemProperties systemProperties){
        JWTUtils.systemProperties = systemProperties;
    }

    /**
     * 生成token
     * @param map 参数
     * @return token
     */
    public static String getToken(Map<String,Object> map) {
        // 默认7填过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v) -> {
            if (!ObjectUtil.isEmpty(v)) {
                builder.withClaim(k, v.toString());
            } else {
                builder.withClaim(k, "");
            }
        });
        // 指定过期时间
        final String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(systemProperties.getSecret()));
        return token;
    }

    /**
     * 验签，签证不通过或抛出异常
     * @param token token
     */
    public static void verify(String token) throws Exception {
        JWT.require(Algorithm.HMAC256(systemProperties.getSecret())).build().verify(token);
    }

}
