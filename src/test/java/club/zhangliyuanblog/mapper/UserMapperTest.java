package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liyuan.zhang
 * @date 2021/3/11 22:16
 */
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    void selectUserByArticleNums() {
        userMapper.selectUserByArticleNums().forEach(System.out::println);
    }
}