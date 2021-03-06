package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface IUserService extends IService<User> {
    User login(User user);

    /**
     * 根据文章数量查询三个用户
     * @return 用户列表
     */
    List<User> selectUserByArticleRanking();

    /**
     * 一个用户是否关注某个用户
     * @param currentUserId 当前用户id
     * @param beAttentionUserId 被关注的用户id
     * @return 是否关注
     */
    Boolean isAttention(Integer currentUserId, Integer beAttentionUserId);

    /**
     * 查询一个用户都关注了哪些用户
     * @param id 当前用户id
     * @return 被关注的用户id的集合
     */
    List<Integer> selectUserIdByAttention(Integer id);

    /** 查询用户信息*/
    UserVo selectUserInfo(Integer currentUserId, Integer userId);
}
