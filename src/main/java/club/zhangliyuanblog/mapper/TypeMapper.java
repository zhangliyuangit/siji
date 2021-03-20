package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.Type;
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
public interface TypeMapper extends BaseMapper<Type> {
    List<String> selectTypesByArticleId(Integer id);
}
