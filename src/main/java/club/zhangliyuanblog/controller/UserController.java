package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Attention;
import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.service.IAttentionService;
import club.zhangliyuanblog.service.IUserService;
import club.zhangliyuanblog.util.JWTUtils;
import club.zhangliyuanblog.vo.LoginUser;
import club.zhangliyuanblog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
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
@SessionAttributes("code")
public class UserController {

    private final IUserService iUserService;
    private ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final IAttentionService iAttentionService;

    public UserController(IUserService iUserService, RedisTemplate<String, String> redisTemplate, IAttentionService iAttentionService) {
        this.iUserService = iUserService;
        this.redisTemplate = redisTemplate;
        this.iAttentionService = iAttentionService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 登录
     * @param loginUser 前端JSON对象
     * @return  统一返回结果集
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser, Model model) {

        // 校验验证码
        String code = redisTemplate.opsForValue().get("code");
        // String code = (String) model.getAttribute("code");
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

    /**
     * 判断是否关注
     * @param currentId 当前用户id
     * @param beAttentionUserId 被关注的id
     * @return  是否官族
     */
    @ApiOperation("查询用户是关注")
    @GetMapping("/isAttention/{currentId}/{beAttentionUserId}")
    public Result isAttention(@PathVariable Integer currentId, @PathVariable Integer beAttentionUserId) {
        return Result.builder()
                .data(iUserService.isAttention(currentId, beAttentionUserId))
                .code(200)
                .message("")
                .build();
    }

    /**
     * 去关注
     * @param currentId 当前用户id
     * @param beAttentionUserId 被关注的用户id
     */
    @ApiOperation("去关注")
    @PostMapping("/toAttention/{currentId}/{beAttentionUserId}")
    public void toAttention(@PathVariable Integer currentId, @PathVariable Integer beAttentionUserId) {
        Attention attention = Attention.builder()
                .be_user_id(beAttentionUserId)
                .user_id(currentId)
                .build();
        iAttentionService.save(attention);
    }

    /**
     * 取消关注
     * @param currentId 当前用户id
     * @param beAttentionUserId 被关注用户的id
     */
    @ApiOperation("取消关注")
    @DeleteMapping("/cancelAttention/{currentId}/{beAttentionUserId}")
    public void cancelAttention(@PathVariable Integer currentId, @PathVariable Integer beAttentionUserId) {
        iAttentionService.remove(new QueryWrapper<Attention>()
                .eq("be_user_id", beAttentionUserId)
                .eq("user_id", currentId));
    }
}
