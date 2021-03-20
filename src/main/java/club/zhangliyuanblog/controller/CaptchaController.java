package club.zhangliyuanblog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author liyuan.zhang
 * @date 2021/3/18 22:38
 * 验证码controller
 */
@Slf4j
@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    private final RedisTemplate<String, String> redisTemplate;

    public CaptchaController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) {
        // HuTool定义图形验证码的长和宽,验证码的位数，干扰线的条数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 20,4,10);
        // 将验证码放入session
        log.info("captcha code is {}", lineCaptcha.getCode());
        //session.setAttribute("code", lineCaptcha.getCode());
        redisTemplate.opsForValue().set("code", lineCaptcha.getCode());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
