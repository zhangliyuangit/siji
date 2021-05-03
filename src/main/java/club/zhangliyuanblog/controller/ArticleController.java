package club.zhangliyuanblog.controller;


import club.zhangliyuanblog.entity.Article;
import club.zhangliyuanblog.service.IArticleService;
import club.zhangliyuanblog.service.IUserService;
import club.zhangliyuanblog.vo.ArticleVo;
import club.zhangliyuanblog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @author liyuan.zhang
 * @since 2021-03-11
 */
@Slf4j
@Api("文章接口")
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final IArticleService iArticleService;
    private final IUserService iUserService;

    public ArticleController(IArticleService iArticleService, IUserService iUserService) {
        this.iArticleService = iArticleService;
        this.iUserService = iUserService;
    }


    /**
     * 根据文章id查询文章，并返回作者信息和类型信息
     * @param id 文章id
     * @return 统一返回结果集
     */
    @ApiOperation("根据id查询文章，返回文章vo")
    @GetMapping("/{id}")
    public Result getOne(@PathVariable(value = "id") Integer id,@RequestParam("currentUserId") Integer currentUserId) {
        Result result;
        try {
            result = Result.builder()
                    .code(200)
                    .data(iArticleService.getOne(id, currentUserId))
                    .message("查询成功")
                    .build();
        } catch (Exception e) {
            result = Result.builder()
                    .message("数据库查询失败")
                    .code(400)
                    .build();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 分页查询文章列表，如果当前有title则模糊匹配
     * 如果没有title则查询全部文章
     * @param currentPage 当前页码
     * @param pageSize  每页显示条数
     * @param title 文章标题。用于模糊搜索
     * @return 统一返回结果集
     */
    @ApiOperation("分页查询文章")
    @GetMapping("/page/{currentPage}/{pageSize}")
    public Result getArticleByPage(@PathVariable(value = "currentPage", required = false) Integer currentPage,
                                   @PathVariable(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(required = false) String title) {
        IPage<Article> page = new Page<>(currentPage, pageSize);
        IPage<Article> iPage = iArticleService.page(page, new QueryWrapper<Article>().like("title", title == null ? "" : title));

        return Result.builder()
                .message("查询成功")
                .code(200)
                .data(iPage)
                .build();
    }


    @ApiOperation("查询当前用户关注的用的文章")
    @GetMapping("/attention/{currentPage}/{pageSize}/{id}")
    public Result getArticleByPageAndUserId(@PathVariable(value = "currentPage", required = false) Integer currentPage,
                                            @PathVariable(value = "pageSize", required = false) Integer pageSize,
                                            @PathVariable(value = "id", required = true) Integer id) {

        // 非空处理
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 5 : pageSize;
        IPage<Article> page = new Page<>(currentPage, pageSize);
        // TODO 这里暂且只使用分页查找，没有加条件
        IPage<Article> iPage = iArticleService.page(page, new QueryWrapper<Article>().in("user_id", iUserService.selectUserIdByAttention(id)));

        return Result.builder()
                .message("查询成功")
                .code(200)
                .data(iPage)
                .build();
    }

    @ApiOperation("保存文章")
    @PostMapping("/save")
    public Result saveArticle(@RequestBody ArticleVo articleVo) {
        try {
            final Integer articleId = iArticleService.saveArticleAndTypes(articleVo);
            return Result.builder().code(200).message("保存成功").data(articleId).build();
        } catch (Exception e) {
            return Result.builder().code(400).message("保存失败").build();
        }
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/deleteArticle/{articleId}")
    public Result deleteArticle(@PathVariable Integer articleId) {
        // 删除文章以及文章类型
        try {
            iArticleService.deleteArticle(articleId);
            return Result.builder().code(200).message("删除成功").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().code(400).message("删除失败").build();
        }
    }


    @ApiOperation("根据文章类型查找文章")
    @GetMapping("/findArticleByType/{type}")
    public Result findArticleByType(@PathVariable String type) {
        return Result.builder().data(iArticleService.selectArticleByType(type)).code(200).build();
    }


    @ApiOperation("根据用户id查询文章")
    @GetMapping("/findArticleByUserId/{userId}")
    public Result findArticlesByUserId(@PathVariable Integer userId) {
        return Result.builder().code(200).data(iArticleService.selectArticleByUserId(userId)).build();
    }

    @ApiOperation(value = "更新文章")
    @PostMapping(value = "/update/article")
    public void updateArticle(@RequestBody Article article) {
        iArticleService.updateById(article);
    }

    @ApiOperation(value = "分类别展示文章信息")
    @GetMapping(value = "/getArticleByLikeAndType/{userId}")
    public Result getArticleByLikeAndType(@PathVariable Integer userId) {
        return Result.builder().message("查询成功").data(iArticleService.selectArticleByLikeAndType(userId)).code(200).build();
    }
}
