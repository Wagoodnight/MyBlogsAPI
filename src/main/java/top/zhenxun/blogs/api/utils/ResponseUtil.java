package top.zhenxun.blogs.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

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
}
