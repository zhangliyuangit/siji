package club.zhangliyuanblog.vo;

import lombok.Data;

/**
 * @author liyuan.zhang
 * @date 2021/3/18 23:16
 */
@Data
public class LoginUser {
    private String phone;
    private String password;
    private String captchaCode;
}
