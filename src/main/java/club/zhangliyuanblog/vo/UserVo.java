package club.zhangliyuanblog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author liyuan.zhang
 * @date 2021/3/28 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {
    private Integer id;

    private String name;

    private Integer age;

    private String phone;

    private String brief;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private String header_pic;

    /** 当前登录的用户，是否关注该用户*/
    private Boolean isAttention;
}
