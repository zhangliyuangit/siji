package club.zhangliyuanblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("s_user")
@ApiModel(value="User对象", description="")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名(非空)")
    private String name;

    @ApiModelProperty(value = "年龄(非空)")
    private Integer age;

    @ApiModelProperty(value = "手机号(非空唯一)")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "简介")
    private String brief;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime create_time;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime update_time;

    @ApiModelProperty(value = "头像地址")
    private String header_pic;


}
