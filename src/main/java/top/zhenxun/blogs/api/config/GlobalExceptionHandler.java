package top.zhenxun.blogs.api.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.utils.ResponseUtil;

import javax.servlet.http.HttpServletResponse;


/**
 * @author ATRI <miko217@xnu.edu.cn>
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获 ServiceException 并处理
     */
    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(ServiceException ex, HttpServletResponse response) {
        // 使用 ResponseUtil 返回异常信息
        ResponseUtil.responseException(response, HttpServletResponse.SC_OK, ex.getCode(), ex.getMessage());
    }

    /**
     * 捕获其他异常，避免程序崩溃
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse response) {
        // 返回 500 错误码和通用错误信息
        ResponseUtil.responseException(response, HttpServletResponse.SC_OK, "500", ex.getMessage());
    }
}
