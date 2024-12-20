package top.zhenxun.blogs.api.service;

import top.zhenxun.blogs.api.pojo.form.LoginForm;
import top.zhenxun.blogs.api.pojo.form.ResetPasswordForm;
import top.zhenxun.blogs.api.pojo.vo.LoginResultVO;
import top.zhenxun.blogs.api.pojo.vo.UserVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public interface UserService {


    /**
     * 登录
     * @param loginForm 登录表单
     * @return 登录用户信息
     */
    public LoginResultVO login(LoginForm loginForm);

    /**
     * 获取登录用户信息
     * @return 登录用户信息
     */
    public UserVO getLoginUserInfo();

    /**
     * 重置密码
     * @param resetPasswordForm 重置密码表单
     */
    public void resetPassword(ResetPasswordForm resetPasswordForm);

    /**
     * 重置邮箱
     * @param email 邮箱
     */
    public void resetEmail(String email);
}
