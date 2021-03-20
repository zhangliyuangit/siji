package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.service.IUserService;
import club.zhangliyuanblog.util.JWTUtils;
import club.zhangliyuanblog.vo.LoginUser;
import club.zhangliyuanblog.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Slf4j
@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;
    private ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public UserController(IUserService iUserService, RedisTemplate<String, String> redisTemplate) {
        this.iUserService = iUserService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 登录
     * @param loginUser 前端JSON对象
     * @return  统一返回结果集
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser, HttpSession session) {

        // 校验验证码
        String code = redisTemplate.opsForValue().get("code");
        if (!loginUser.getCaptchaCode().equals(code)) {
            return Result.builder()
                    .message("验证码不正确！！！")
                    .code(400)
                    .build();
        }
        Result result;
        try {
            User user = new User();
            BeanUtils.copyProperties(loginUser, user);
            User userDb = iUserService.login(user);
            // 因为这里要存token所以要把密码清掉
            userDb.setPassword("");
            Map map = objectMapper.readValue(objectMapper.writeValueAsString(userDb), HashMap.class);
            result = Result.builder()
                    .code(200)
                    .message("登陆成功")
                    .data(JWTUtils.getToken(map))
                    .build();
        } catch (Exception e) {
            result = Result.builder().code(400).message("登陆失败").build();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询用户榜单
     * @return 统一返回结果集
     */
    @ApiOperation("查询用户榜")
    @GetMapping("/ranking")
    public Result selectUserByArticleNums() {
        return Result.builder()
                .code(200)
                .data(iUserService.selectUserByArticleRanking())
                .message("查询成功")
                .build();
    }

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public Result selectUserDetails(@PathVariable(value = "id") Integer id) {
        return Result.builder()
                .message("查询成功")
                .code(200)
                .data(iUserService.getById(id))
                .build();
    }
}
