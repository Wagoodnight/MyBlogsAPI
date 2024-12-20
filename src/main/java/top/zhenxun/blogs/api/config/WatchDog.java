package top.zhenxun.blogs.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 定义切点，匹配所有 Controller 的方法
     */
    @Pointcut("execution(* top.zhenxun.blogs.api.controller..*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        try {
            String ip = getClientIp(request);
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

    /**
     * 获取客户端真实IP
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            // X-Forwarded-For 可能包含多个IP地址，取第一个非unknown的IP
            return xForwardedFor.split(",")[0].trim();
        }
        String proxyClientIp = request.getHeader("Proxy-Client-IP");
        if (proxyClientIp != null && !"unknown".equalsIgnoreCase(proxyClientIp)) {
            return proxyClientIp;
        }
        String wlProxyClientIp = request.getHeader("WL-Proxy-Client-IP");
        if (wlProxyClientIp != null && !"unknown".equalsIgnoreCase(wlProxyClientIp)) {
            return wlProxyClientIp;
        }
        return request.getRemoteAddr();
    }
}
