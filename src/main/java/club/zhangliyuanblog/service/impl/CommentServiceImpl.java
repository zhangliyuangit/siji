package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.mapper.CommentMapper;
import club.zhangliyuanblog.mapper.UserMapper;
import club.zhangliyuanblog.service.ICommentService;
import club.zhangliyuanblog.vo.CommentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public CommentServiceImpl(CommentMapper commentMapper, UserMapper userMapper) {
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<CommentVo> addAndGet(Comment comment) {
        comment.setCreate_time(LocalDateTime.now());
        commentMapper.insert(comment);
        final List<CommentVo> commentVos = commentMapper
                .selectList(new QueryWrapper<Comment>().eq("article_id", comment.getArticle_id()))
                .stream().map(comment1 -> {
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(comment1, commentVo);
                    commentVo.setUser(userMapper.selectById(comment1.getUser_id()));
                    return commentVo;
                }).collect(Collectors.toList());
        return commentVos;
    }
}
