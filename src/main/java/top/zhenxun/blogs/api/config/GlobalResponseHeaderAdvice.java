package top.zhenxun.blogs.api.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@ControllerAdvice
public class GlobalResponseHeaderAdvice {

    @ModelAttribute
    public void setGlobalCacheControl(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();

        if (uri.startsWith("/api/file/download/")) {
            response.setHeader("Cache-Control", "max-age=31536000");
        } else {
            response.setHeader("Cache-Control", "no-cache");
        }
    }
}
