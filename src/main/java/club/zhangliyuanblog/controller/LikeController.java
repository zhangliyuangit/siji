package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Like;
import club.zhangliyuanblog.service.ILikeService;
import club.zhangliyuanblog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Api("文章点赞")
@RestController
@RequestMapping("/like")
public class LikeController {

    private final ILikeService iLikeService;

    public LikeController(ILikeService iLikeService) {
        this.iLikeService = iLikeService;
    }

    @ApiOperation("给一篇文章点赞")
    @PutMapping("/like/{articleId}/{currentUserId}")
    public void likeArticle(@PathVariable Integer articleId, @PathVariable Integer currentUserId) {
        iLikeService.save(new Like(null, currentUserId, articleId));
    }

    @ApiOperation("取消点赞")
    @DeleteMapping("/noLike/{articleId}/{currentUserId}")
    public void noLikeArticle(@PathVariable Integer articleId, @PathVariable Integer currentUserId) {
        iLikeService.remove(new QueryWrapper<Like>()
                .eq("user_id", currentUserId)
                .eq("article_id", articleId));
    }
}
