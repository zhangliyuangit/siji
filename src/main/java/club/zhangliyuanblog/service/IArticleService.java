package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface IArticleService extends IService<Article> {
    ArticleVo getOne(Integer id);
}
