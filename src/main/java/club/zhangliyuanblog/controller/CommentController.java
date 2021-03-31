package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.service.ICommentService;
import club.zhangliyuanblog.vo.CommentVo;
import club.zhangliyuanblog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
}
