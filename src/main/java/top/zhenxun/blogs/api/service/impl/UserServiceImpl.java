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
import top.zhenxun.blogs.api.utils.*;

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

    @Autowired
    private RedisUtils redisUtils;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public LoginResultVO login(LoginForm loginForm) {
        String ip = ResponseUtil.getClientIp(request);
        String redisKey = "login_fail_count:" + ip;
        String blockKey = "login_blocked_ip:" + ip;
        // 检查 IP 是否被封禁
        if (redisUtils.hasKey(blockKey)) {
            // 如果被封禁，则直接返回 403 禁止访问
            log.warn("IP {} is blocked, access denied", ip);
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }

        SystemInfo systemInfo = systemMapper.selectById(1);
        if (systemInfo.getEnableTurnstile() == 1) {
            if (!TurnstileUtil.validateToken(loginForm.getCfToken(), systemInfo.getSecretKey())) {
                //人机验证失败也增加计数
                addFailCount(redisKey, blockKey, ip);
                throw new ServiceException(ResponseType.TURNSTILE_VERIFICATION_FAILED);
            }
        }
        loginForm.setPassword(EncryptUtil.encrypt(loginForm.getPassword()));
        UserVO userVO = userMapper.login(loginForm);
        if (userVO == null) {
            //登录失败增加计数
            addFailCount(redisKey, blockKey, ip);
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

    private void addFailCount(String redisKey, String blockKey,String ip) {
        // 获取当前 IP 的失败次数
        Integer requestCount = (Integer) redisUtils.get(redisKey);

        // 如果请求次数不存在，初始化为 0
        if (requestCount == null) {
            requestCount = 0;
            redisUtils.set(redisKey, requestCount, Const.FAIL2BAN_FIND_TIME);
        }
        if (requestCount >= Const.FAIL2BAN_MAX_TRY) {
            // 如果请求次数超过阈值，封禁 IP
            redisUtils.set(blockKey, true, Const.FAIL2BAN_BAN_TIME);
            log.warn("IP({}) login fails more than {} times", ip, Const.FAIL2BAN_MAX_TRY);
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }

        // 如果没有封禁，则继续增加请求次数
        redisUtils.incr(redisKey, 1);
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
