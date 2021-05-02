package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface CommentMapper extends BaseMapper<Comment> {
    Integer selectNumByArticleId(@Param("articleId") Integer articleId);
}
