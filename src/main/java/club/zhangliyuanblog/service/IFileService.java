package club.zhangliyuanblog.service;

import club.zhangliyuanblog.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author liyuan.zhang
 * @since 2021-04-19
 */
public interface IFileService extends IService<File> {

    /**
     * 根据用户id查询文件数量
     * @param userId 用户id
     * @return 文件数量
     */
    Integer findFileNumsByUserId(Integer userId);

}
