package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.mapper.ArticleMapper;
import club.zhangliyuanblog.mapper.CommentMapper;
import club.zhangliyuanblog.mapper.UserMapper;
import club.zhangliyuanblog.service.IArticleService;
import club.zhangliyuanblog.vo.ArticleVo;
import club.zhangliyuanblog.vo.CommentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final ArticleMapper articleMapper;

    private final UserMapper userMapper;

    private final CommentMapper commentMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, UserMapper userMapper, CommentMapper commentMapper) {
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public ArticleVo getOne(Integer id) {
        ArticleVo articleVo = articleMapper.selectArticleById(id);
        // 将文章的作者绑到对象上
        User user = userMapper.selectById(articleVo.getUser_id());
        articleVo.setUser(user);

        // 将文章所有的评论和对应评论的用户绑定
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("article_id", articleVo.getId()));
        List<CommentVo> commentVos = comments.stream()
                .map(comment -> {
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(comment, commentVo);
                    commentVo.setUser(userMapper.selectById(comment.getUser_id()));
                    return commentVo;
                }).collect(Collectors.toList());
        articleVo.setComments(commentVos);
        return articleVo;
    }
}
