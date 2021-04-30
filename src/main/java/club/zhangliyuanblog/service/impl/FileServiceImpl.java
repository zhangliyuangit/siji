package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.File;
import club.zhangliyuanblog.mapper.FileMapper;
import club.zhangliyuanblog.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-04-19
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Integer findFileNumsByUserId(Integer userId) {
        return fileMapper.findFileNumsByUserId(userId);
    }
}
