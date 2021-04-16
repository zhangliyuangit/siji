package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 添加评论并查询所有
     * @param comment 评论实体
     * @return  评论列表
     */
    List<CommentVo> addAndGet(Comment comment);

    /**
     * 根据文章id查询commentVos
     * @param articleId 文章id
     * @return commentVo集合
     */
    List<CommentVo> getCommentsByArticleId(Integer articleId);
}
