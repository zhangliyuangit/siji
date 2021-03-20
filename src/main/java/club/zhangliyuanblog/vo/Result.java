package club.zhangliyuanblog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 13:02
 * 统一返回结果集
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private     Integer     code;
    private     String      message;
    private     Object      data;
}
