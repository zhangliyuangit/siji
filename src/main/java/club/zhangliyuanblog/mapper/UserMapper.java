package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Attention;
import club.zhangliyuanblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUserByArticleNums();

    /**
     * 查询当前用户关注了哪些用户
     * @param id 当前用户id
     * @return 用户列表
     */
    List<User> selectAttentionUser(Integer id);

    /**
     * 查询当前用被哪些用户关注了
     * @return  关注当前用户的用户列表
     */
    List<User> selectUsersBeAttention(Integer id);

    /**
     * 查询一个用户是否关注一个用户
     * @param currentUserId 当前用户id
     * @param beAttentionUserId 被关注用户id
     * @return  关注
     */
    Attention selectIsAttention(@Param("currentUserId") Integer currentUserId, @Param("beAttentionUserId") Integer beAttentionUserId);
}
