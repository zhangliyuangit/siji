package club.zhangliyuanblog.exception;

import club.zhangliyuanblog.vo.Result;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 22:37
 */
@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(value = TokenExpiredException.class)
    public Result tokenExpiredExceptionRes() {
        return Result.builder()
                .code(400)
                .message("Token已经过期!!!")
                .build();
    }
    @ExceptionHandler(value = SignatureVerificationException.class)
    public Result signatureVerificationExceptionRes() {
        log.info("出现SignatureVerificationException");
        return Result.builder()
                .code(400)
                .message("签名错误!!!")
                .build();
    }

    @ExceptionHandler(value = AlgorithmMismatchException.class)
    public Result algorithmMismatchExceptionRes() {
        return Result.builder()
                .code(400)
                .message("加密算法不匹配!!!")
                .build();
    }


    @ExceptionHandler(value = NoTokenException.class)
    public Result noTokenExceptionRes() {
        log.info("出现NoTokenException");
        return Result.builder()
                .code(400)
                .message("请添加token!!!")
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionRes(Exception e) {
        return Result.builder()
                .code(400)
                .message(e.getMessage())
                .build();
    }
}
