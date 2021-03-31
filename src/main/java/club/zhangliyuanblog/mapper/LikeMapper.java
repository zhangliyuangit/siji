package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Like;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface LikeMapper extends BaseMapper<Like> {
    /**
     * 查询一篇文章的点赞数
     * @param articleId 文章id
     * @return  点赞数
     */
    Integer likeNum(Integer articleId);
}
