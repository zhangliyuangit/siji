package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.User;
import club.zhangliyuanblog.mapper.UserMapper;
import club.zhangliyuanblog.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return Optional.ofNullable(userMapper.selectOne(new QueryWrapper<User>()
                .eq("phone", user.getPhone())
                .eq("password", user.getPassword())))
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<User> selectUserByArticleRanking() {
        return userMapper.selectUserByArticleNums();
    }

    @Override
    public Boolean isAttention(Integer currentUserId, Integer beAttentionUserId) {
        return userMapper.selectIsAttention(currentUserId, beAttentionUserId) != null;
    }
}
