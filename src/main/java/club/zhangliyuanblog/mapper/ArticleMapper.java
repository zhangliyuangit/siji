package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.vo.ArticleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface ArticleMapper extends BaseMapper<Article> {
    ArticleVo selectArticleById(Integer id);

    List<Article> selectArticleByType(String type);

    List<Article> selectArticleByUserId(Integer userId);
}
