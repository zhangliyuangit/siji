package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
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
}
