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
    private boolean debug;

    private String secret;
}
