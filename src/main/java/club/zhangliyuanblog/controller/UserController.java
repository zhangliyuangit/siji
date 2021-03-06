package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.config.ServerConfig;
import club.zhangliyuanblog.entity.Attention;
import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.properties.SystemProperties;
import club.zhangliyuanblog.service.IAttentionService;
import club.zhangliyuanblog.service.IUserService;
import club.zhangliyuanblog.util.JWTUtils;
import club.zhangliyuanblog.util.ZhenziUtils;
import club.zhangliyuanblog.vo.LoginUser;
import club.zhangliyuanblog.vo.Result;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Slf4j
@Api("用户接口")
@RestController
@RequestMapping("/user")
@SessionAttributes("code")
@EnableConfigurationProperties(SystemProperties.class)
public class UserController {

    private final IUserService iUserService;
    private ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final IAttentionService iAttentionService;
    private final SystemProperties systemProperties;
    private final ServerConfig serverConfig;

    public UserController(IUserService iUserService, RedisTemplate<String, String> redisTemplate, IAttentionService iAttentionService, SystemProperties systemProperties, ServerConfig serverConfig) {
        this.iUserService = iUserService;
        this.redisTemplate = redisTemplate;
        this.iAttentionService = iAttentionService;
        this.systemProperties = systemProperties;
        this.serverConfig = serverConfig;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 账号密码登录
     * @param loginUser 前端JSON对象
     * @return  统一返回结果集
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result passwordLogin(@RequestBody LoginUser loginUser, Model model) {

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
     * 免密登录
     * @param phone 手机号
     * @param checkCode 验证码
     * @return 统一返回结果集
     */
    @ApiOperation("免密登录")
    @PostMapping("/phone")
    public Result PhoneLoginOrRegister(String phone, String checkCode) throws JsonProcessingException {
        String c = redisTemplate.opsForValue().get(phone);
        if (!checkCode.equals(c)) {
            return Result.builder().message("验证码错误或失效!!!").code(400).build();
        }
        // 有当前手机号的情况下
        User user = iUserService.getOne(new QueryWrapper<User>().eq("phone", phone), false);
        if (ObjectUtil.isNotEmpty(user)) {
            Map map = objectMapper.readValue(objectMapper.writeValueAsString(user), HashMap.class);
            return Result.builder().code(200).message("").data(JWTUtils.getToken(map)).build();
        }
        // 当前用户不存在,创建用户
        User newUser = User.builder()
                .name("用户" + System.currentTimeMillis())
                .create_time(LocalDateTime.now())
                .phone(phone)
                .password(("siji_" + System.currentTimeMillis()).substring(0, 18))
                .update_time(LocalDateTime.now())
                .build();
        iUserService.save(newUser);
        Map map = objectMapper.readValue(objectMapper.writeValueAsString(newUser), HashMap.class);
        return Result.builder().code(200).message("").data(JWTUtils.getToken(map)).build();
    }

    /**
     * 获取验证码
     * @throws Exception
     */
    @ApiOperation("获取收集验证码")
    @GetMapping("/phoneCode")
    public String getPhoneCode(String phone, HttpSession session) throws Exception {
        ZhenziSmsClient client = new ZhenziSmsClient(ZhenziUtils.API_URL, ZhenziUtils.APP_ID ,ZhenziUtils.APP_SECRET);
        Map<String, Object> params = new HashMap<>(16);
        params.put("number", phone);
        params.put("templateId", "2175");
        String[] templateParams = new String[2];
        String phoneCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        templateParams[0] = phoneCode;
        templateParams[1] = "5分钟";
        // 将code存入redis,并设置过期时间为5分钟
        redisTemplate.opsForValue().set(phone, phoneCode, 60 * 5, TimeUnit.SECONDS);
        params.put("templateParams", templateParams);

        // 如果是debug模式，不发送验证码
        if (systemProperties.isDebug()){
            log.info("验证码是{}", phoneCode);
            return "isDebug";
        }
        // 发送验证码
        return client.send(params);
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
    @GetMapping("/{id}/{currentUserId}")
    public Result selectUserDetails(@PathVariable(value = "id") Integer id, @PathVariable Integer currentUserId) {
        return Result.builder()
                .message("查询成功")
                .code(200)
                .data(iUserService.selectUserInfo(currentUserId, id))
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


    @ApiOperation("更新头像")
    @PostMapping("/updateHeaderImg/{currentUserId}")
    public Result updateUserHeaderImg(@RequestParam MultipartFile file, @PathVariable Integer currentUserId) {
        log.info("调用地址为{}", serverConfig.getUrl());
        log.info("用户id为{}", currentUserId);
        try {
            String fileName = System.currentTimeMillis() + ".jpg";
            String path = systemProperties.getImgPath() + File.separator + fileName;
            // 保存文件
            file.transferTo(new File(path));
            // 更新用户头像
            User user = User.builder()
                    .id(currentUserId)
                    .header_pic(serverConfig.getUrl() + fileName)
                    .update_time(LocalDateTime.now())
                    .build();
            iUserService.updateById(user);
            User userDb = iUserService.getById(currentUserId);
            userDb.setPassword("");
            Map map = objectMapper.readValue(objectMapper.writeValueAsString(userDb), HashMap.class);
            return Result.builder().code(200).data(JWTUtils.getToken(map)).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.builder().code(400).message("修改失败").build();
        }
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user) throws JsonProcessingException {
        user.setUpdate_time(LocalDateTime.now());
        iUserService.updateById(user);
        User userDb = iUserService.getById(user.getId());
        userDb.setPassword("");
        Map map = objectMapper.readValue(objectMapper.writeValueAsString(userDb), HashMap.class);
        return Result.builder().code(200).data(JWTUtils.getToken(map)).build();
    }
    @ApiOperation("更新密码操作")
    @PostMapping("/updateUserPassword/{currentUserId}")
    public Result updateUserPassWord(@RequestBody LoginUser user, @PathVariable Integer currentUserId) {
        String c = redisTemplate.opsForValue().get(user.getPhone());
        if (!user.getCaptchaCode().equals(c)) {
            return Result.builder().message("验证码错误或失效!!!").code(400).build();
        }
        User u = new User();
        BeanUtils.copyProperties(user, u);
        u.setUpdate_time(LocalDateTime.now());
        u.setId(currentUserId);
        iUserService.updateById(u);
        return Result.builder().code(200).build();
    }

}

