package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.vo.ArticleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface ArticleMapper extends BaseMapper<Article> {
    ArticleVo selectArticleById(Integer id);

    List<Article> selectArticleByType(String type);

    List<Article> selectArticleByUserId(Integer userId);

    /**
     * 查询我点赞过的文章
     * @param userId 用户id
     * @return 文章列表
     */
    List<ArticleVo> selectArticleByLike(@Param("userId") Integer userId);
}
