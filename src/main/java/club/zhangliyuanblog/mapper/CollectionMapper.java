package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.entity.Collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface CollectionMapper extends BaseMapper<Collection> {

    /**
     * 根据收藏夹id查询所有收藏集内的文章
     * @param favoriteId 收藏夹id
     * @return  文章
     */
    List<Article> selectArticleByFavoriteId(@Param("favoriteId") Integer favoriteId);
}
