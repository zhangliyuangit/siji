package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Article;
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

    @ApiOperation(value = "根据用户id查询所有收藏夹")
    @GetMapping(value = "/findFavorite/{userId}")
    public Result findFavoriteByUserId(@PathVariable(value = "userId") Integer userId) {
        List<Collection> collectionList = collectionService.list(new QueryWrapper<Collection>().eq("user_id", userId));
        return Result.builder().data(collectionList).code(200).message("查询成功").build();
    }


    @ApiOperation(value = "更新收藏夹")
    @PostMapping(value = "/update")
    public Result updateFavorite(@RequestBody Collection collection) {
        collectionService.updateById(collection);
        return Result.builder().message("更新成功").code(200).build();
    }

    @ApiOperation(value = "删除收藏夹")
    @DeleteMapping(value = "/delete/{favoriteId}")
    public void deleteFavoriteById(@PathVariable Integer favoriteId) {
        // 删除收藏夹内容
        collectionContentService.remove(new QueryWrapper<CollectionContent>().eq("collection_id", favoriteId));
        // 删除收藏集
        collectionService.removeById(favoriteId);
    }

    @ApiOperation(value = "根据收藏夹查询收藏夹内容")
    @GetMapping(value = "/findCollectionContent/{collectionId}")
    public Result findContentByCollectionId(@PathVariable(value = "collectionId") Integer collectionId) {
        List<Article> articleList = collectionService.selectArticleByFavoriteId(collectionId);
        return Result.builder().code(200).message("查询成功").data(articleList).build();
    }

    @ApiOperation(value = "根据id查询具体的收藏夹信息")
    @GetMapping(value = "/get/{id}")
    public Result getFavoriteById(@PathVariable(value = "id") Integer favoriteId) {
        return Result.builder().data(collectionService.getById(favoriteId)).code(200).message("查询成功").build();
    }

}
