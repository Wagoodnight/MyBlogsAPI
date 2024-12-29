package top.zhenxun.blogs.api.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.entity.Link;
import top.zhenxun.blogs.api.entity.Tools;
import top.zhenxun.blogs.api.entity.User;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.mapper.LinkMapper;
import top.zhenxun.blogs.api.mapper.ToolsMapper;
import top.zhenxun.blogs.api.mapper.UserMapper;
import top.zhenxun.blogs.api.pojo.CurrentLoginUser;
import top.zhenxun.blogs.api.pojo.Role;
import top.zhenxun.blogs.api.pojo.form.LinkAddForm;
import top.zhenxun.blogs.api.pojo.form.ToolsAddForm;
import top.zhenxun.blogs.api.pojo.query.Pages;
import top.zhenxun.blogs.api.pojo.vo.LinkVO;
import top.zhenxun.blogs.api.pojo.vo.ToolsVO;
import top.zhenxun.blogs.api.service.MiscService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Service
public class MiscServiceImpl implements MiscService {

    private static final Logger log = LoggerFactory.getLogger(MiscServiceImpl.class);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private ToolsMapper toolsMapper;

    @Override
    public void addLink(LinkAddForm linkAddForm) {
        authCheck();
        Link link = new Link();
        BeanUtils.copyProperties(linkAddForm, link);
        try {
            linkMapper.insert(link);
        } catch (Exception e) {
            log.error("add link error", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void addTools(ToolsAddForm toolsAddForm) {
        authCheck();
        Tools tools = new Tools();
        BeanUtils.copyProperties(toolsAddForm, tools);
        try {
            toolsMapper.insert(tools);
        } catch (Exception e) {
            log.error("add tools error", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public IPage<LinkVO> selectLinkByPage(Pages pages) {
        Page<LinkVO> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());
        return linkMapper.selectLinkPage(page);
    }

    @Override
    public IPage<ToolsVO> selectToolsByPage(Pages pages) {
        Page<ToolsVO> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());
        return toolsMapper.selectToolsPage(page);
    }

    @Override
    public void deleteLink(Long id) {
        authCheck();
        try {
            linkMapper.deleteById(id);
        } catch (Exception e) {
            log.error("delete link error", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void deleteTools(Long id) {
        authCheck();
        try {
            toolsMapper.deleteById(id);
        } catch (Exception e) {
            log.error("delete tools error", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    private void authCheck() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        User user = userMapper.selectById(loginUser.getUserId());
        if (!user.getRole().equals(Role.admin)) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
    }
}
