package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface IArticleService extends IService<Article> {
    ArticleVo getOne(Integer id, Integer currentUserId);

    /**
     * 保存文章，并且保存类型
     * @param articleVo
     */
    Integer saveArticleAndTypes(ArticleVo articleVo);

    /**
     * 删除文章和文章对应的类型
     * @param articleId 文章id
     */
    void deleteArticle(Integer articleId);

    /**
     * 根据类型查询文章集合
     * @param type 类型
     * @return  文章集合
     */
    List<Article> selectArticleByType(String type);

    /**
     * 根据用户id查询文章
     * @param userId 用户id
     * @return 文章集合
     */
    List<ArticleVo> selectArticleByUserId(Integer userId);

    /**
     * 查询当前用户点赞的所有文章
     * 并根据类型分类
     * @param userId
     * @return
     */
    Map<String, Set<ArticleVo>> selectArticleByLikeAndType(Integer userId);
}
