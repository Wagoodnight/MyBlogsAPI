package top.zhenxun.blogs.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import top.zhenxun.blogs.api.pojo.Role;
import top.zhenxun.blogs.api.pojo.form.SystemInfoUpdateForm;
import top.zhenxun.blogs.api.pojo.vo.SystemInfoVO;
import top.zhenxun.blogs.api.service.SystemService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Service
public class SystemServiceImpl implements SystemService {

    private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public SystemInfoVO getSystemInfo() {
        SystemInfo systemInfo = systemMapper.selectById(1);
        SystemInfoVO systemInfoVO = new SystemInfoVO();
        BeanUtils.copyProperties(systemInfo, systemInfoVO);
        if (!authCheck()) {
            systemInfoVO.setSecretKey(null);
            systemInfoVO.setSiteUrl(null);
        }
        return systemInfoVO;
    }

    @Override
    public void updateSystemInfo(SystemInfoUpdateForm systemInfoUpdateForm) {
        if (!authCheck()) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        SystemInfo systemInfo = new SystemInfo();
        BeanUtils.copyProperties(systemInfoUpdateForm, systemInfo);
        systemInfo.setId(1);
        systemMapper.updateById(systemInfo);
    }

    private boolean authCheck() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            return false;
        }
        User user = userMapper.selectById(loginUser.getUserId());
        return user.getRole().equals(Role.admin);
    }
}
