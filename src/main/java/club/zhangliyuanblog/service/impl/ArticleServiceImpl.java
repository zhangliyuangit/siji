package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.*;
import club.zhangliyuanblog.mapper.*;
import club.zhangliyuanblog.service.IArticleService;
import club.zhangliyuanblog.vo.ArticleVo;
import club.zhangliyuanblog.vo.CommentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final ArticleMapper articleMapper;

    private final UserMapper userMapper;

    private final CommentMapper commentMapper;

    private final LikeMapper likeMapper;

    private final TypeMapper typeMapper;

    private final ArticleTypeMapper articleTypeMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, UserMapper userMapper, CommentMapper commentMapper, LikeMapper likeMapper, TypeMapper typeMapper, ArticleTypeMapper articleTypeMapper) {
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.likeMapper = likeMapper;
        this.typeMapper = typeMapper;
        this.articleTypeMapper = articleTypeMapper;
    }

    @Override
    public ArticleVo getOne(Integer id, Integer currentUserId) {
        ArticleVo articleVo = articleMapper.selectArticleById(id);
        // 将文章的作者绑到对象上
        User user = userMapper.selectById(articleVo.getUser_id());
        articleVo.setUser(user);

        // 将文章所有的评论和对应评论的用户绑定
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("article_id", articleVo.getId()).orderByDesc("create_time"));
        List<CommentVo> commentVos = comments.stream()
                .map(comment -> {
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(comment, commentVo);
                    commentVo.setUser(userMapper.selectById(comment.getUser_id()));
                    return commentVo;
                }).collect(Collectors.toList());
        articleVo.setComments(commentVos);
        // 是否关注该用户
        articleVo.setIsAttention(userMapper.selectIsAttention(currentUserId, user.getId()) != null);
        // 是否点赞
        articleVo.setIsLike(likeMapper.selectOne(new QueryWrapper<Like>()
                .eq("user_id", currentUserId)
                .eq("article_id", id)) != null);
        // 点赞数
        articleVo.setLikeNum(likeMapper.likeNum(id));
        return articleVo;
    }

    @Override
    public Integer saveArticleAndTypes(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);
        article.setCreate_time(LocalDateTime.now());
        // 这里因为前端传过来的是一个空串
        if ("".equals(article.getThematic())) {
            article.setThematic(null);
        }
        articleMapper.insert(article);

        // 拿到现有的所以类型，id作为key，name作为value
        Map<Integer, String> allTypes = typeMapper.selectList(null).stream()
                .collect(Collectors.toMap(Type::getId, Type::getName));

        // 保存文章类型
        articleVo.getTypes()
                .forEach(type -> {
                    // 证明这个type是本身就存在的
                    if (allTypes.containsValue(type)) {
                        articleTypeMapper.insert(new ArticleType(null, article.getId(), selectKeyByValue(allTypes, type)));
                    }else {
                        // 创建一个新的type
                        Type newType = Type.builder().create_time(LocalDateTime.now()).name(type).build();
                        typeMapper.insert(newType);
                        articleTypeMapper.insert(new ArticleType(null, article.getId(), newType.getId()));
                    }
                });
        // 这里自增id
        return article.getId();
    }

    @Override
    public void deleteArticle(Integer articleId) {
        // 删除文章
        articleMapper.deleteById(articleId);
        // 删除文章类型
        articleTypeMapper.delete(new QueryWrapper<ArticleType>()
                .eq("article_id", articleId));
    }

    @Override
    public List<Article> selectArticleByType(String type) {
        return articleMapper.selectArticleByType(type);
    }

    @Override
    public List<ArticleVo> selectArticleByUserId(Integer userId) {
        List<ArticleVo> articleVoList = articleMapper.selectArticleByUserId(userId).stream().map(article -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setTypes(typeMapper.selectTypesByArticleId(article.getId()));
            return articleVo;
        }).collect(Collectors.toList());
        return articleVoList;
    }


    /**
     * 根据value查找key
     * @param map map
     * @param value value
     * @return key
     */
    private Integer selectKeyByValue(Map<Integer, String> map, String value) {
        for (Integer key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
}
