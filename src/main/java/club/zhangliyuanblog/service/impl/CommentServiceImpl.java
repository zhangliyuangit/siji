package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Comment;
import club.zhangliyuanblog.mapper.CommentMapper;
import club.zhangliyuanblog.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
