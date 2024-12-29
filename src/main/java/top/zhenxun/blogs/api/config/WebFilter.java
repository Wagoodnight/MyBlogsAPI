package top.zhenxun.blogs.api.config;


import org.springframework.context.annotation.Configuration;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.pojo.CurrentLoginUser;
import top.zhenxun.blogs.api.utils.ResponseUtil;
import top.zhenxun.blogs.api.utils.TokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li
 */
@Configuration
public class WebFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        String uri = request.getRequestURI();
        if(uri.endsWith(".js")) {
            response.setContentType("text/javascript; utf-8");
            filterChain.doFilter(request, response);
            return;
        }
        if(uri.contains("/getVerify") || uri.contains("/webjars") || uri.contains("/api-docs") || uri.contains("/swagger")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");

        if (token != null) {
            if (!TokenUtil.verify(token)) {
                ResponseUtil.responseException(response, HttpServletResponse.SC_OK, ResponseType.NOT_LOGIN.getCode(), ResponseType.NOT_LOGIN.getMsg());
                return;
            }
            CurrentLoginUser loginUser = TokenUtil.getPayload(token);
            request.setAttribute(Const.CURRENT_LOGIN_USER, loginUser);
        }

        filterChain.doFilter(request, response);
    }
}
