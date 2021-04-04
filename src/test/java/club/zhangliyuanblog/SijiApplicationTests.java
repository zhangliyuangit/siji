package club.zhangliyuanblog;

import club.zhangliyuanblog.util.ZhenziUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SijiApplicationTests {

    @Test
    void contextLoads() throws Exception {
        ZhenziSmsClient client = new ZhenziSmsClient(ZhenziUtils.API_URL, ZhenziUtils.APP_ID ,ZhenziUtils.APP_SECRET);
        Map<String, Object> params = new HashMap<>(16);
        params.put("number", "13029905545");
        params.put("templateId", "2175");
        String[] templateParams = new String[2];
        templateParams[0] = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);
        // 发送验证码
        String send = client.send(params);
        System.out.println(send);
    }

}
