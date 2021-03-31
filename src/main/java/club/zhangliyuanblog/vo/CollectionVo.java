package club.zhangliyuanblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author liyuan.zhang
 * @date 2021/3/31 21:00
 * 收藏夹VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionVo {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime create_time;

    private Integer user_id;

    private String name;

    /** 表示当前登陆的用户是否收藏了本篇文章*/
    private Boolean isCollect;
}
