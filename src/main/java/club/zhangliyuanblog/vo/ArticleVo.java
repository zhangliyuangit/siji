package club.zhangliyuanblog.vo;

import club.zhangliyuanblog.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liyuan.zhang
 * @date 2021/3/11 22:01
 */
@Data
public class ArticleVo {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String context;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime create_time;

    private String thematic;

    private Integer user_id;

    private List<String> types;

    private User user;

    /** 评论内容*/
    private List<CommentVo> comments;
}
