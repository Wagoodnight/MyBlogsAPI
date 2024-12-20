package top.zhenxun.blogs.api.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.entity.SystemInfo;
import top.zhenxun.blogs.api.entity.User;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.mapper.SystemMapper;
import top.zhenxun.blogs.api.mapper.UserMapper;
import top.zhenxun.blogs.api.pojo.CurrentLoginUser;
import top.zhenxun.blogs.api.pojo.form.LoginForm;
import top.zhenxun.blogs.api.pojo.form.ResetPasswordForm;
import top.zhenxun.blogs.api.pojo.vo.LoginResultVO;
import top.zhenxun.blogs.api.pojo.vo.UserVO;
import top.zhenxun.blogs.api.service.UserService;
import top.zhenxun.blogs.api.utils.EncryptUtil;
import top.zhenxun.blogs.api.utils.TokenUtil;
import top.zhenxun.blogs.api.utils.TurnstileUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public LoginResultVO login(LoginForm loginForm) {
        SystemInfo systemInfo = systemMapper.selectById(1);
        if (systemInfo.getEnableTurnstile() == 1) {
            if (!TurnstileUtil.validateToken(loginForm.getCfToken(), systemInfo.getSecretKey())) {
                throw new ServiceException(ResponseType.TURNSTILE_VERIFICATION_FAILED);
            }
        }
        loginForm.setPassword(EncryptUtil.encrypt(loginForm.getPassword()));
        UserVO userVO = userMapper.login(loginForm);
        if (userVO == null) {
            throw new ServiceException(ResponseType.LOGIN_FAILED);
        }
        CurrentLoginUser currentLoginUser = new CurrentLoginUser();
        currentLoginUser.setUserId(userVO.getUserId());
        currentLoginUser.setRole(userVO.getRole());
        String token = TokenUtil.createLoginToken(currentLoginUser);
        LoginResultVO loginResultVO = new LoginResultVO();
        BeanUtils.copyProperties(userVO, loginResultVO);
        loginResultVO.setToken(token);
        User user = new User();
        user.setUserId(userVO.getUserId());
        user.setLastLoginTime(System.currentTimeMillis());
        userMapper.updateById(user);
        return loginResultVO;
    }

    @Override
    public UserVO getLoginUserInfo() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        User user = userMapper.selectById(loginUser.getUserId());
        if (user == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public void resetPassword(ResetPasswordForm resetPasswordForm) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        User user = userMapper.selectById(loginUser.getUserId());
        if (user == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        if (user.getPassword().equals(EncryptUtil.encrypt(resetPasswordForm.getOldPassword()))) {
            try {
                user.setPassword(EncryptUtil.encrypt(resetPasswordForm.getNewPassword()));
                userMapper.updateById(user);
            } catch (Exception e) {
                throw new ServiceException(ResponseType.SERVER_ERR);
            }

        } else {
            throw new ServiceException(ResponseType.OLD_PASSWORD_ERROR);
        }
    }

    @Override
    public void resetEmail(String email) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        User user = userMapper.selectById(loginUser.getUserId());
        if (user == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        user.setEmail(email);
        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }
}
