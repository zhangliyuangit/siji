package club.zhangliyuanblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liyuan.zhang
 * @date 2021/3/31 16:58
 */
@SpringBootTest
class LikeMapperTest {

    @Autowired
    private LikeMapper likeMapper;

    @Test
    void likeNum() {
        System.out.println(likeMapper.likeNum(1));
    }
}