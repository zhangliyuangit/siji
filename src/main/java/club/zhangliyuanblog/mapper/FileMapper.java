package club.zhangliyuanblog.mapper;

import club.zhangliyuanblog.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author liyuan.zhang
 * @since 2021-04-19
 */
public interface FileMapper extends BaseMapper<File> {

    Integer findFileNumsByUserId(@Param("userId") Integer userId);
}
