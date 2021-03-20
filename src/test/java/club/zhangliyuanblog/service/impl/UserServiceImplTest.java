package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 23:01
 */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private IUserService iUserService;


    @Test
    public void test() {
        User one = iUserService.getOne(new QueryWrapper<User>()
                .eq("phone", "18625815964")
                .eq("password", "123"));
        System.out.println(one);
    }
}