package club.zhangliyuanblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liyuan.zhang
 * @date 2021/3/11 22:12
 */
@SpringBootTest
class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;


    @Test
    void selectArticleById() {
        System.out.println(articleMapper.selectArticleById(1));
    }

    @Test
    void selectArticleByType() {
        articleMapper.selectArticleByType("java").forEach(System.out::println);
    }
}