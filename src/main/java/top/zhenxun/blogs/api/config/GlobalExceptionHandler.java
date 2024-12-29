package top.zhenxun.blogs.api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.utils.RedisUtils;
import top.zhenxun.blogs.api.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 捕获 ServiceException 并处理
     */
    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(ServiceException ex, HttpServletResponse response) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        // 获取 ResponseType
        ResponseType responseType = ResponseType.getResponseType(ex.getCode());

        if (Objects.equals(responseType, ResponseType.UNAUTHORIZED)) {
            HttpServletRequest request = attributes.getRequest();
            String ip = ResponseUtil.getClientIp(request);
            if (addFailCount(ip)) {
                response.setHeader("X-Banned-IP", ip);
            }
        }

        // 使用 ResponseUtil 返回异常信息
        ResponseUtil.responseException(response, HttpServletResponse.SC_OK, ex.getCode(), ex.getMessage());
    }

    private Boolean addFailCount(String ip) {
        String redisKey = "forbidden_count:" + ip;
        String blockKey = "forbidden_blocked_ip:" + ip;

        if (redisUtils.hasKey(blockKey)) {
            log.warn("IP({}) is blocked", ip);
            return true;
        }

        // 获取当前 IP 的失败次数
        Integer requestCount = (Integer) redisUtils.get(redisKey);

        // 如果请求次数不存在，初始化为 0
        if (requestCount == null) {
            requestCount = 0;
            redisUtils.set(redisKey, requestCount, Const.FAIL2BAN_FIND_TIME);
        }
        if (requestCount >= Const.FAIL2BAN_MAX_TRY) {
            // 如果请求次数超过阈值，封禁 IP
            redisUtils.set(blockKey, true, Const.FAIL2BAN_BAN_TIME);
            redisUtils.del(redisKey);
            log.warn("IP({}) forbidden more than {} times", ip, Const.FAIL2BAN_MAX_TRY);
            return true;
        }

        // 如果没有封禁，则继续增加请求次数
        redisUtils.incr(redisKey, 1);
        return false;
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
