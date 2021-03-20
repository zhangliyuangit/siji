package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.vo.ArticleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface ArticleMapper extends BaseMapper<Article> {
    ArticleVo selectArticleById(Integer id);
}
