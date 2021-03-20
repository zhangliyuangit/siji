package club.zhangliyuanblog.exception;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 22:09
 * 自定义 没有token异常
 */
public class NoTokenException extends Exception{
    /**无参构造函数*/
    public NoTokenException(){
        super();
    }

    /**用详细信息指定一个异常*/
    public NoTokenException(String message){
        super(message);
    }

    /**用指定的详细信息和原因构造一个新的异常*/
    public NoTokenException(String message, Throwable cause){
        super(message,cause);
    }

    /**用指定原因构造一个新的异常*/
    public NoTokenException(Throwable cause) {
        super(cause);
    }
}

