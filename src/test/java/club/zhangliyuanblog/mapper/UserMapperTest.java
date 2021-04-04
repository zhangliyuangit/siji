package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

    @Test
    void selectAttentionUser() {
        userMapper.selectAttentionUser(3).forEach(System.out::println);
    }

    @Test
    void selectUsersBeAttention() {
        userMapper.selectUsersBeAttention(1).forEach(System.out::println);
    }

    @Test
    void selectIsAttention() {
        System.out.println(userMapper.selectIsAttention(2, 1));
    }

    @Test
    public void test1() {
        System.out.println(userMapper.selectOne(new QueryWrapper<User>().eq("name", "我不存在")));
    }
}