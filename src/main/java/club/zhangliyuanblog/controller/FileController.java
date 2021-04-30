package club.zhangliyuanblog.controller;

import club.zhangliyuanblog.properties.SystemProperties;
import club.zhangliyuanblog.service.IFileService;
import club.zhangliyuanblog.vo.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;

/**
 * @author liyuan.zhang
 * @date 2021/4/17 21:58
 */
@Slf4j
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {


    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private IFileService iFileService;

    @ApiOperation("上传文件,并返回文件名称")
    @PostMapping(value = "/uploadFile")
    public Result uploadFile(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.builder().message("上传失败，请选择文件").code(400).build();
        }
        // 获取文件名称
        String filename = file.getOriginalFilename();
        // 获取文件后缀名
        assert filename != null;
        String suffix = filename.substring(filename.lastIndexOf("."));
        // 文件重命名
        filename = System.currentTimeMillis() + suffix;
        log.info("文件名称为：{}", filename);
        try {
            // 保存文件
            file.transferTo(new File(systemProperties.getTargetPath(), filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将文件名返回给前端
        return Result.builder().code(200).message("上传成功").data(filename).build();

    }

    @ApiOperation("下载文件")
    @PostMapping("/download/{fileId}")
    public void downloadTemplate(HttpServletResponse response, @PathVariable Integer fileId){
        // 数据库获取文件
        club.zhangliyuanblog.entity.File localFile = iFileService.getById(fileId);
        File file = new File(systemProperties.getTargetPath() + "\\" + localFile.getFile_name());
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            //判断文件父目录是否存在
            if (file.exists()) {
                //设置返回文件信息
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode("userTemplate.xls","UTF-8"));
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while(bis.read(buffer) != -1){
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ApiOperation("保存文件信息")
    @PostMapping("/saveFile")
    public Result saveFile(@RequestBody club.zhangliyuanblog.entity.File file) {
        try {
            // 设置上传文件的日期
            file.setCreate_time(LocalDateTime.now());
            iFileService.save(file);
            return Result.builder().code(200).message("上传文件成功").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().code(400).message("上传文件失败").build();
        }
    }

    @ApiOperation("分页查看文件")
    @GetMapping("/page/{currentPage}/{pageSize}")
    public Result fileListByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        Page<club.zhangliyuanblog.entity.File> page = new Page<>(currentPage, pageSize);
        Page<club.zhangliyuanblog.entity.File> filePage = iFileService.page(page);
        return Result.builder().message("查询成功").code(200).data(filePage).build();
    }

    @ApiOperation("根据用户id查看文件数")
    @GetMapping(value = "/fileNums/{userId}")
    public Result findNumByUserId(@PathVariable(value = "userId") Integer userId) {
        Integer fileNums = iFileService.findFileNumsByUserId(userId);
        return Result.builder().code(200).data(fileNums).build();
    }
}



