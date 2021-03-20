package club.zhangliyuanblog.vo;


import club.zhangliyuanblog.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author liyuan.zhang
 * @date 2021/3/20 9:38
 * 评论  里面绑定user对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Integer id;

    private Integer user_id;

    private Integer article_id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime create_time;

    private String context;

    private User user;
}
