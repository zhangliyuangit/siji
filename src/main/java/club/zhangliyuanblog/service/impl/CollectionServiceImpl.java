package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Collection;
import club.zhangliyuanblog.entity.CollectionContent;
import club.zhangliyuanblog.mapper.CollectionContentMapper;
import club.zhangliyuanblog.mapper.CollectionMapper;
import club.zhangliyuanblog.service.ICollectionService;
import club.zhangliyuanblog.vo.CollectionVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements ICollectionService {

    private final CollectionMapper collectionMapper;

    private final CollectionContentMapper collectionContentMapper;
    public CollectionServiceImpl(CollectionMapper collectionMapper, CollectionContentMapper collectionContentMapper) {
        this.collectionMapper = collectionMapper;
        this.collectionContentMapper = collectionContentMapper;
    }

    @Override
    public List<Collection> addAndGet(Collection collection) {
        collectionMapper.insert(collection);
        return collectionMapper.selectList(new QueryWrapper<Collection>().eq("user_id", collection.getUser_id()));
    }

    @Override
    public List<CollectionVo> getCollectionsByUserId(Integer userId, Integer articleId) {
        List<CollectionVo> collectionVos = collectionMapper.selectList(new QueryWrapper<Collection>().eq("user_id", userId)).stream()
                .map(collection -> {
                    CollectionVo collectionVo = new CollectionVo();
                    BeanUtils.copyProperties(collection, collectionVo);
                    // 判断是否收藏过本文章
                    CollectionContent collectionContent = collectionContentMapper.selectOne(new QueryWrapper<CollectionContent>()
                            .eq("collection_id", collection.getId())
                            .eq("article_id", articleId));
                    collectionVo.setIsCollect(!ObjectUtil.isNull(collectionContent));
                    return collectionVo;
                }).collect(Collectors.toList());
        return collectionVos;
    }
}
