package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Collection;
import club.zhangliyuanblog.entity.CollectionContent;
import club.zhangliyuanblog.service.ICollectionContentService;
import club.zhangliyuanblog.service.ICollectionService;
import club.zhangliyuanblog.vo.CollectionVo;
import club.zhangliyuanblog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Api("收藏夹接口")
@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final ICollectionService collectionService;
    private final ICollectionContentService collectionContentService;

    public CollectionController(ICollectionService collectionService, ICollectionContentService collectionContentService) {
        this.collectionService = collectionService;
        this.collectionContentService = collectionContentService;
    }

    @ApiOperation("创建收藏夹，并返回当前用户的所有收藏夹")
    @PostMapping("/create/{articleId}")
    public Result createFavorite(@RequestBody Collection collection, @PathVariable Integer articleId) {
        collection.setCreate_time(LocalDateTime.now());
        List<CollectionVo> collections = collectionService.addAndGet(collection, articleId);
        return Result.builder().code(200).data(collections).build();
    }

    @ApiOperation("查询当前用户的所有收藏夹，并且查询当前收藏夹是否收藏本篇文章")
    @GetMapping("/findCollection/{currentUserId}/{articleId}")
    public Result getCollectionListByUserId(@PathVariable Integer currentUserId, @PathVariable Integer articleId) {
        return Result.builder().data(collectionService.getCollectionsByUserId(currentUserId, articleId)).code(200).build();
    }

    @ApiOperation("去收藏")
    @PostMapping("/toCollect")
    public void toCollect(@RequestBody CollectionContent collectionContent) {
        collectionContentService.save(collectionContent);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/cancelCollect/{collectionId}/{articleId}")
    public void cancelCollect(@PathVariable Integer collectionId, @PathVariable Integer articleId) {
        collectionContentService.remove(new QueryWrapper<CollectionContent>()
                .eq("collection_id", collectionId)
                .eq("article_id", articleId));
    }
}
