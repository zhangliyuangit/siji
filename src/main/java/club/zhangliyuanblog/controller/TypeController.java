package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.service.ITypeService;
import club.zhangliyuanblog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    private final ITypeService iTypeService;

    public TypeController(ITypeService iTypeService) {
        this.iTypeService = iTypeService;
    }

    @GetMapping("/getAllTypes")
    public Result getAllTypes() {
        return Result.builder()
                .code(200)
                .data(iTypeService.list())
                .build();
    }
}
