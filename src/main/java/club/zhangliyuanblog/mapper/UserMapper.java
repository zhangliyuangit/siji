package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUserByArticleNums();
}
