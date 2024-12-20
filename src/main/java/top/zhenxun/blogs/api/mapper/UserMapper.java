package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.User;
import top.zhenxun.blogs.api.pojo.form.LocalResetPasswordForm;
import top.zhenxun.blogs.api.pojo.form.LoginForm;
import top.zhenxun.blogs.api.pojo.vo.UserVO;

import java.util.List;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 登录
     * @param loginForm 登录表单
     * @return 登录用户信息
     */
    UserVO login(@Param("loginForm") LoginForm loginForm);

    /**
     * 获取邮箱列表
     * @return 邮箱列表
     */
    List<String> emailList();

    /**
     * 重置密码
     * @param localResetPasswordForm 重置密码表单
     * @return 是否成功
     */
    int updatePassword(@Param("localResetPasswordForm") LocalResetPasswordForm localResetPasswordForm);
}
