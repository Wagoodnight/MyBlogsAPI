package top.zhenxun.blogs.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.entity.Blog;
import top.zhenxun.blogs.api.entity.Profile;
import top.zhenxun.blogs.api.entity.Tags;
import top.zhenxun.blogs.api.entity.User;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.mapper.BlogsMapper;
import top.zhenxun.blogs.api.mapper.ProfileMapper;
import top.zhenxun.blogs.api.mapper.TagsMapper;
import top.zhenxun.blogs.api.mapper.UserMapper;
import top.zhenxun.blogs.api.pojo.CurrentLoginUser;
import top.zhenxun.blogs.api.pojo.Role;
import top.zhenxun.blogs.api.pojo.form.BlogsAddFrom;
import top.zhenxun.blogs.api.pojo.form.BlogsReEditForm;
import top.zhenxun.blogs.api.pojo.form.ProfileForm;
import top.zhenxun.blogs.api.pojo.form.TagUpdateForm;
import top.zhenxun.blogs.api.pojo.query.BlogFuzzySearchQuery;
import top.zhenxun.blogs.api.pojo.query.BlogsQuery;
import top.zhenxun.blogs.api.pojo.vo.BlogListVO;
import top.zhenxun.blogs.api.pojo.vo.BlogsVO;
import top.zhenxun.blogs.api.pojo.vo.ProfileVO;
import top.zhenxun.blogs.api.pojo.vo.TagCount;
import top.zhenxun.blogs.api.service.BlogsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Service
public class BlogsServiceImpl implements BlogsService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private BlogsMapper blogsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private ProfileMapper profileMapper;


    @Override
    public IPage<BlogListVO> selectBlogsByPage(BlogsQuery blogsQuery) {
        if (blogsQuery.getIsDelete() == 1 || blogsQuery.getStatus() == 0) {
            authCheck();
        }
        Page<BlogListVO> page = new Page<>(blogsQuery.getCurrentPage(), blogsQuery.getPageSize());
        return blogsMapper.blogsPage(page, blogsQuery);
    }

    @Override
    public IPage<BlogListVO> blogsFuzzySearch(BlogFuzzySearchQuery blogFuzzySearchQuery) {
        Page<BlogListVO> page = new Page<>(blogFuzzySearchQuery.getCurrentPage(), blogFuzzySearchQuery.getPageSize());
        return blogsMapper.fuzzySearchPage(page, blogFuzzySearchQuery);
    }

    @Override
    public BlogsVO selectBlogsById(Long id) {
        BlogsVO blogsVO = new BlogsVO();
        Blog blog = blogsMapper.selectById(id);
        if (blog == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        if (blog.getStatus() == 0 || blog.getIsDelete() == 1) {
            authCheck();
        }
        BeanUtils.copyProperties(blog, blogsVO);
        return blogsVO;
    }

    @Override
    public Long selectBlogsByRandom() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1).eq("is_delete", 0).orderByAsc("RANDOM()").last("limit 1");
        Blog blog = blogsMapper.selectOne(queryWrapper);
        if (blog == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        return blog.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBlogs(BlogsAddFrom blogsAddFrom) {
        authCheck();
        log.info("Add a new blog: {}", blogsAddFrom);
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogsAddFrom, blog);
        blog.setIsDelete(0);
        blog.setCreateTime(System.currentTimeMillis());
        if (blog.getStatus() == 0) {
            blog.setPublishTime(null);
        } else {
            blog.setPublishTime(System.currentTimeMillis());
        }
        try {
            blogsMapper.insert(blog);
        } catch (Exception e) {
            log.error("Insert new blog ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(BlogsReEditForm blogsReEditForm) {
        authCheck();
        Blog blog = blogsMapper.selectById(blogsReEditForm.getId());
        if (blog == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        if (blog.getStatus() == 0 && blogsReEditForm.getStatus() == 1) {
            blog.setPublishTime(System.currentTimeMillis());
        }
        BeanUtils.copyProperties(blogsReEditForm, blog);
        blog.setUpdateTime(System.currentTimeMillis());
        try {
            blogsMapper.updateById(blog);
        } catch (Exception e) {
            log.error("Update blog ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postBlog(Long id) {
        authCheck();
        Blog blog = blogsMapper.selectById(id);
        if (blog == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        if (blog.getStatus() == 1 && blog.getIsDelete() == 0) {
            return;
        }
        blog.setIsDelete(0);
        blog.setStatus(1);
        blog.setPublishTime(System.currentTimeMillis());
        try {
            blogsMapper.updateById(blog);
        } catch (Exception e) {
            log.error("Post blog ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSoftBlog(Long id) {
        authCheck();
        Blog blog = blogsMapper.selectById(id);
        if (blog == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        if (blog.getIsDelete() == 1) {
            return;
        }
        blog.setIsDelete(1);
        try {
            blogsMapper.updateById(blog);
        } catch (Exception e) {
            log.error("Delete blog ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHardBlog(Long id) {
        authCheck();
        if (blogsMapper.deleteById(id) < 1) {
            log.error("Delete blog ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public List<Tags> selectTags() {
        return tagsMapper.selectTags();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTags(String name) {
        authCheck();
        try {
            tagsMapper.insert(new Tags(name));
        } catch (Exception e) {
            log.error("Insert new tag ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
        log.info("Add a new tag: {}", name);
    }

    @Override
    public void deleteTags(Integer tagId) {
        authCheck();
        if (tagsMapper.deleteById(tagId) < 1) {
            log.error("Delete tag ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void renameTags(TagUpdateForm tagUpdateForm) {
        authCheck();
        Tags tags = tagsMapper.selectById(tagUpdateForm.getTagId());
        if (tags == null) {
            throw new ServiceException(ResponseType.NOT_FOUND);
        }
        tags.setTagName(tagUpdateForm.getTagName());
        try {
            tagsMapper.updateById(tags);
        } catch (Exception e) {
            log.error("Update tag ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(ProfileForm profileForm) {
        authCheck();
        Profile profile = new Profile();
        BeanUtils.copyProperties(profileForm, profile);
        profile.setId(Const.PROFILE_ID);
        try {
            profileMapper.updateProfile(profile);
        } catch (Exception e) {
            log.error("Update profile ERROR!", e);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public ProfileVO getProfile() {
        Profile profile = profileMapper.selectById(Const.PROFILE_ID);
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(profile, profileVO);
        return profileVO;
    }

    @Override
    public void testToken() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser == null) {
            throw new ServiceException(ResponseType.NOT_LOGIN);
        }
        User user = userMapper.selectById(loginUser.getUserId());
        if (!user.getRole().equals(Role.admin)) {
            throw new ServiceException(ResponseType.NOT_LOGIN);
        }
        log.info("User({}) auto login success!", loginUser.getUserId());
    }

    @Override
    public List<TagCount> selectTagCount() {
        return tagsMapper.selectTagCount();
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
