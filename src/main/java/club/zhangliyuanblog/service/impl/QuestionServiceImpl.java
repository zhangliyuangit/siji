package club.zhangliyuanblog.service.impl;

import club.zhangliyuanblog.entity.Question;
import club.zhangliyuanblog.mapper.QuestionMapper;
import club.zhangliyuanblog.service.IQuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
