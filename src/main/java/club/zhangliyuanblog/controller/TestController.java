package club.zhangliyuanblog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyuan.zhang
 * @date 2021/3/14 11:31
 */
@Api("测试接口")
@RestController
public class TestController {


    @ApiOperation("测试JWT")
    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
