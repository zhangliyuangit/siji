package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.service.ICommentService;
import club.zhangliyuanblog.vo.CommentVo;
import club.zhangliyuanblog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Api("评论接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation("添加评论")
    @PostMapping("/addAndGet")
    public Result addComment(@RequestBody Comment comment) {
        List<CommentVo> comments = commentService.addAndGet(comment);
        return Result.builder().data(comments).code(200).message("添加成功").build();
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/deleteComment/{commentId}/{articleId}")
    public Result deleteComment(@PathVariable Integer commentId, @PathVariable Integer articleId) {
        try {
            commentService.removeById(commentId);
            List<CommentVo> commentVos = commentService.getCommentsByArticleId(articleId);
            return Result.builder().code(200).message("删除成功").data(commentVos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().code(400).message("删除失败").build();
        }
    }
}
