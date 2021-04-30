package club.zhangliyuanblog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 22:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "system")
public class SystemProperties {
    /** 是否是debug模式*/
    private boolean debug;

    /** JWT密钥*/
    private String secret;

    /** 本地服务器图片路径*/
    private String imgPath;

    /** 本地服务器文件路径*/
    private String targetPath;
}
