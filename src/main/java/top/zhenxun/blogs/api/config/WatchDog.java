package top.zhenxun.blogs.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.utils.RedisUtils;
import top.zhenxun.blogs.api.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Aspect
@Component
public class WatchDog {
    private static final Logger log = LogManager.getLogger(WatchDog.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 定义切点，匹配所有 Controller 的方法
     */
    @Pointcut("execution(* top.zhenxun.blogs.api.controller..*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint joinPoint) throws ServiceException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        try {
            String ip = ResponseUtil.getClientIp(request); // 获取请求的IP地址
            long currentSecond = System.currentTimeMillis() / 1000; // 当前秒
            String redisKey = "request_count:" + ip + ":" + currentSecond; // 使用当前秒来区分

            String blockKey = "blocked_ip:" + ip; // 被封禁的 Redis key
            String loginBlockKey = "login_blocked_ip:" + ip; // 登录失败被封禁的 Redis key
            String forbiddenBlockKet = "forbidden_blocked_ip:" + ip; // 权限验证失败被封禁的 Redis key

            // 检查 IP 是否被封禁
            if (redisUtils.hasKey(blockKey)) {
                throw new ServiceException(ResponseType.UNAUTHORIZED);
            }
            if (redisUtils.hasKey(loginBlockKey)) {
                throw new ServiceException(ResponseType.UNAUTHORIZED);
            }
            if (redisUtils.hasKey(forbiddenBlockKet)) {
                throw new ServiceException(ResponseType.UNAUTHORIZED);
            }

            // 设置每秒钟最大请求次数
            int maxRequestPerSecond = Const.MAX_REQUEST_PER_SECOND;

            // 获取当前 IP 在当前秒内的请求次数
            Integer requestCount = (Integer) redisUtils.get(redisKey);

            // 如果请求次数不存在，初始化为 0
            if (requestCount == null) {
                requestCount = 0;
                // 如果是首次请求，则设置该键的过期时间为 1 秒
                redisUtils.set(redisKey, requestCount, 1);
            }

            // 如果请求次数超出最大请求次数，封禁 IP
            if (requestCount >= maxRequestPerSecond) {
                // 设置封禁 IP
                redisUtils.set(blockKey, true, Const.REQUEST_LIMIT_BLOCK_PERIOD);

                // 记录封禁日志
                log.warn("IP {} has been blocked for 10 minutes due to excessive requests", ip);
                throw new ServiceException(ResponseType.UNAUTHORIZED);
            }

            // 如果没有封禁，则继续增加请求次数
            redisUtils.incr(redisKey, 1);

            // 记录请求信息
            int port = request.getRemotePort();
            String uri = request.getRequestURI();
            String method = request.getMethod();
            String queryParams = request.getQueryString();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method methodInfo = signature.getMethod();
            Object[] args = joinPoint.getArgs();

            Object[] filteredArgs = filterArgs(args);
            String params = objectMapper.writeValueAsString(filteredArgs);

            log.info("{}:{} -> {} {}, Query Params: {}, Params: {}, Handler: {}",
                    ip, port, method, uri, queryParams, params, methodInfo.getName());

        } catch (ServiceException e) {
            // 处理拒绝访问的情况
            log.warn("Request from IP {} was denied", ResponseUtil.getClientIp(request));
            throw e;
        } catch (Exception e) {
            log.error("Error logging request information: {}", e.getMessage(), e);
        }
    }


    /**
     * 过滤掉无法序列化的参数
     */
    private Object[] filterArgs(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) arg;
                        Map<String, Object> fileInfo = new HashMap<>();
                        fileInfo.put("fileName", file.getOriginalFilename());
                        fileInfo.put("size", file.getSize());
                        return Collections.unmodifiableMap(fileInfo);
                    }
                    return arg;
                }).toArray();
    }
}
