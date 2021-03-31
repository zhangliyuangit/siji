package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Collection;
import club.zhangliyuanblog.service.ICollectionService;
import club.zhangliyuanblog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    public CollectionController(ICollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @ApiOperation("创建收藏夹，并返回当前用户的所有收藏夹")
    @PostMapping("/create")
    public Result createFavorite(@RequestBody Collection collection) {
        collection.setCreate_time(LocalDateTime.now());
        List<Collection> collections = collectionService.addAndGet(collection);
        return Result.builder().code(200).data(collections).build();
    }

    @ApiOperation("查询当前用户的所有收藏夹，并且查询当前收藏夹是否收藏本篇文章")
    @GetMapping("/findCollection/{currentUserId}/{articleId}")
    public Result getCollectionListByUserId(@PathVariable Integer currentUserId, @PathVariable Integer articleId) {
        return Result.builder().data(collectionService.getCollectionsByUserId(currentUserId, articleId)).code(200).build();
    }
}
