package top.zhenxun.blogs.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.zhenxun.blogs.api.entity.Tags;
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

import java.util.List;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public interface BlogsService {

    /**
     * 分页查询博客
     * @param blogsQuery 查询表单
     * @return 查询结果
     */
    IPage<BlogListVO> selectBlogsByPage(BlogsQuery blogsQuery);

    /**
     * 模糊查询博客
     * @param blogFuzzySearchQuery 查询表单
     * @return 查询结果
     */
    IPage<BlogListVO> blogsFuzzySearch(BlogFuzzySearchQuery blogFuzzySearchQuery);

    /**
     * 根据id查询博客
     * @param id 博客id
     * @return 博客详情
     */
    BlogsVO selectBlogsById(Long id);

    /**
     * 随机查询博客
     * @return 博客ID
     */
    Long selectBlogsByRandom();

    /**
     * 新增博客
     * @param blogsAddFrom 博客表单
     */
    void addBlogs(BlogsAddFrom blogsAddFrom);

    /**
     * 查询标签
     * @return 标签列表
     */
    List<Tags> selectTags();

    /**
     * 新增标签
     * @param name 标签名
     */
    void addTags(String name);

    /**
     * 删除标签
     * @param tagId 标签id
     */
    void deleteTags(Integer tagId);

    /**
     * 重命名标签
     * @param tagUpdateForm 标签更新表单
     */
    void renameTags(TagUpdateForm tagUpdateForm);

    /**
     * 更新个人信息
     * @param profileForm 个人信息表单
     */
    void updateProfile(ProfileForm profileForm);

    /**
     * 更新博客
     * @param blogsReEditForm 博客表单
     */
    void updateBlog(BlogsReEditForm blogsReEditForm);

    /**
     * 发布博客(针对草稿箱中的和已删除的)
     * @param id 博客id
     */
    void postBlog(Long id);

    /**
     * 软删除博客
     * @param id 博客id
     */
    void deleteSoftBlog(Long id);

    /**
     * 彻底删除博客
     * @param id 博客id
     */
    void deleteHardBlog(Long id);

    /**
     * 获取个人信息
     * @return 个人信息
     */
    ProfileVO getProfile();

    /**
     * 测试token
     */
    void testToken();

    /**
     * 获取标签统计
     * @return 标签统计
     */
    List<TagCount> selectTagCount();
}
