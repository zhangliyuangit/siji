package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.Collection;
import club.zhangliyuanblog.vo.CollectionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface ICollectionService extends IService<Collection> {
    /**
     * 创建collection并返回当前用户所有collection
     * @param collection collection
     * @return  所有collection
     */
    List<Collection> addAndGet(Collection collection);

    /**
     * 根据用户id查询所有收藏夹
     * @param userId    用户id
     * @param articleId 文章id
     * @return  收藏夹vo集合
     */
    List<CollectionVo> getCollectionsByUserId(Integer userId, Integer articleId);
}
