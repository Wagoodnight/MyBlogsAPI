package top.zhenxun.blogs.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import top.zhenxun.blogs.api.common.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Slf4j
public class ResponseUtil {

    // 现有的responseJson方法
    public static void responseJson(HttpServletResponse response, int status, Object data) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);
            response.getWriter().write(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // 新增的responseException方法
    public static void responseException(HttpServletResponse response, int status, String code, String message) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);

            // 构造返回的JSON格式数据
            String result = JSON.toJSONString(new ExceptionResponse(code, message), SerializerFeature.WriteMapNullValue);
            response.getWriter().write(result);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // 定义一个内部类用于构建异常返回的JSON对象
    public static class ExceptionResponse {
        private String code;
        private String message;

        public ExceptionResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 获取客户端真实IP
     */
    public static String getClientIp(HttpServletRequest request) {
        //先获取自定义阿里云CDN头部
        String clientIp = request.getHeader(Const.ALIYUN_CDN_HEADER);
        if (clientIp != null && !clientIp.isEmpty()) {
            return clientIp;
        }

        // 获取 X-Forwarded-For 头部
        String xForwardedFor = request.getHeader("X-Forwarded-For");

        // 如果 X-Forwarded-For 头部存在且不是 "unknown"
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            // 分割 X-Forwarded-For 头部中的 IP 地址
            String[] ips = xForwardedFor.split(",");

            // 检查 IP 数量是否超过2个（CDN和Nginx是两个可信代理）
            if (ips.length > 2) {
                // 如果超过2个，认为是由不可信来源添加的，只取倒数第二个（即CDN设置的），倒数第一个是Nginx设置的
                return ips[ips.length - 2].trim();
            } else {
                // 否则取第一个非 "unknown" 的 IP 地址
                for (String ip : ips) {
                    if (!"unknown".equalsIgnoreCase(ip.trim())) {
                        return ip.trim();
                    }
                }
            }
        }

        // 如果 X-Forwarded-For 不可用或所有值都是 "unknown"，尝试使用其他头部
        String proxyClientIp = request.getHeader("Proxy-Client-IP");
        if (proxyClientIp != null && !"unknown".equalsIgnoreCase(proxyClientIp)) {
            return proxyClientIp;
        }

        String wlProxyClientIp = request.getHeader("WL-Proxy-Client-IP");
        if (wlProxyClientIp != null && !"unknown".equalsIgnoreCase(wlProxyClientIp)) {
            return wlProxyClientIp;
        }

        // 如果所有头部都不可用或为 "unknown"，返回远程地址
        return request.getRemoteAddr();
    }
}
