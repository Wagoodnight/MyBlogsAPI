package top.zhenxun.blogs.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhenxun.blogs.api.common.Response;
import top.zhenxun.blogs.api.pojo.form.LoginForm;
import top.zhenxun.blogs.api.pojo.form.ResetPasswordForm;
import top.zhenxun.blogs.api.pojo.vo.LoginResultVO;
import top.zhenxun.blogs.api.service.UserService;

/**
 * <p>
 * 用户模块接口，前端控制器
 * </p>
 *
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Api(tags = "用户模块接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("登陆")
    @PostMapping("/login")
    public Response<LoginResultVO> login(@RequestBody LoginForm loginForm) {
        return Response.success(userService.login(loginForm));
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/info")
    public Response<?> info() {
        return Response.success(userService.getLoginUserInfo());
    }

    @ApiOperation("修改密码")
    @PostMapping("/changePassword")
    public Response<?> changePassword(@RequestBody ResetPasswordForm resetPasswordForm) {
        userService.resetPassword(resetPasswordForm);
        return Response.success();
    }

    @ApiOperation("修改邮箱")
    @GetMapping("/changeEmail")
    public Response<?> changeEmail(String email) {
        userService.resetEmail(email);
        return Response.success();
    }
}
