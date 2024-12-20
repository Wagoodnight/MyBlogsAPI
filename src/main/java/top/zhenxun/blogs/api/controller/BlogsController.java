package top.zhenxun.blogs.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhenxun.blogs.api.common.Response;
import top.zhenxun.blogs.api.entity.Tags;
import top.zhenxun.blogs.api.pojo.form.BlogsAddFrom;
import top.zhenxun.blogs.api.pojo.form.BlogsReEditForm;
import top.zhenxun.blogs.api.pojo.form.ProfileForm;
import top.zhenxun.blogs.api.pojo.form.TagUpdateForm;
import top.zhenxun.blogs.api.pojo.query.BlogFuzzySearchQuery;
import top.zhenxun.blogs.api.pojo.query.BlogsQuery;
import top.zhenxun.blogs.api.pojo.vo.BlogListVO;
import top.zhenxun.blogs.api.pojo.vo.BlogsVO;
import top.zhenxun.blogs.api.service.impl.BlogsServiceImpl;

import java.util.List;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */

@Api(tags = "博客模块接口")
@RestController
@RequestMapping("/blogs")
public class BlogsController {

    @Autowired
    private BlogsServiceImpl blogsService;

    @ApiOperation("获取博客列表")
    @PostMapping("/list")
    public Response<IPage<BlogListVO>> getGroupList(@RequestBody BlogsQuery blogsQuery) {
        return Response.success(blogsService.selectBlogsByPage(blogsQuery));
    }

    @ApiOperation("模糊查询博客")
    @PostMapping("/fuzzySearch")
    public Response<IPage<BlogListVO>> fuzzySearch(@RequestBody BlogFuzzySearchQuery blogFuzzySearchQuery) {
        return Response.success(blogsService.blogsFuzzySearch(blogFuzzySearchQuery));
    }

    @ApiOperation("获取博客详情")
    @GetMapping("/detail")
    public Response<BlogsVO> getDetail(Long id) {
        return Response.success(blogsService.selectBlogsById(id));
    }

    @ApiOperation("随机获取博客")
    @GetMapping("/random")
    public Response<?> getRandom() {
        return Response.success(blogsService.selectBlogsByRandom());
    }

    @ApiOperation("新增博客")
    @PostMapping("/add")
    public Response<?> addBlogs(@RequestBody BlogsAddFrom blogsAddFrom) {
        blogsService.addBlogs(blogsAddFrom);
        return Response.success();
    }

    @ApiOperation("获取博客标签列表")
    @GetMapping("/tags")
    public Response<List<Tags>> getTags() {
        return Response.success(blogsService.selectTags());
    }

    @ApiOperation("新增标签")
    @GetMapping("/addTags")
    public Response<?> addTags(String name) {
        blogsService.addTags(name);
        return Response.success();
    }

    @ApiOperation("删除标签")
    @GetMapping("/deleteTags")
    public Response<?> deleteTags(Integer tagId) {
        blogsService.deleteTags(tagId);
        return Response.success();
    }

    @ApiOperation("重命名标签")
    @PostMapping("/renameTags")
    public Response<?> renameTags(@RequestBody TagUpdateForm tagUpdateForm) {
        blogsService.renameTags(tagUpdateForm);
        return Response.success();
    }

    @ApiOperation("更新个人信息")
    @PostMapping("/updateProfile")
    public Response<?> updateProfile(@RequestBody ProfileForm profileForm) {
        blogsService.updateProfile(profileForm);
        return Response.success();
    }

    @ApiOperation("获取个人信息")
    @GetMapping("/getProfile")
    public Response<?> getProfile() {
        return Response.success(blogsService.getProfile());
    }

    @ApiOperation("测试token")
    @GetMapping("/testToken")
    public Response<?> testToken() {
        blogsService.testToken();
        return Response.success("Miko");
    }

    @ApiOperation("获取标签统计")
    @GetMapping("/tagCount")
    public Response<?> getTagCount() {
        return Response.success(blogsService.selectTagCount());
    }

    @ApiOperation("更新博客")
    @PostMapping("/updateBlog")
    public Response<?> updateBlog(@RequestBody BlogsReEditForm blogsReEditForm) {
        blogsService.updateBlog(blogsReEditForm);
        return Response.success();
    }

    @ApiOperation("发布博客")
    @GetMapping("/postBlog")
    public Response<?> postBlog(Long id) {
        blogsService.postBlog(id);
        return Response.success();
    }

    @ApiOperation("软删除博客")
    @GetMapping("/deleteSoftBlog")
    public Response<?> deleteSoftBlog (Long id) {
        blogsService.deleteSoftBlog(id);
        return Response.success();
    }

    @ApiOperation("彻底删除博客")
    @GetMapping("/deleteHardBlog")
    public Response<?> deleteHardBlog (Long id) {
        blogsService.deleteHardBlog(id);
        return Response.success();
    }
}
