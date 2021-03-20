package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Type;
import club.zhangliyuanblog.mapper.TypeMapper;
import club.zhangliyuanblog.service.ITypeService;
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
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

}
