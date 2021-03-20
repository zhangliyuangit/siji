package club.zhangliyuanblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@MapperScan("club.zhangliyuanblog.mapper")
@SpringBootApplication
@EnableConfigurationProperties
public class SijiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SijiApplication.class, args);
    }

}
