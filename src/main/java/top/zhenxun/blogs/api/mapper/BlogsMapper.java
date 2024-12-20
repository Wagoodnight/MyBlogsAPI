package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.Blog;
import top.zhenxun.blogs.api.pojo.query.BlogFuzzySearchQuery;
import top.zhenxun.blogs.api.pojo.query.BlogsQuery;
import top.zhenxun.blogs.api.pojo.vo.BlogListVO;
import top.zhenxun.blogs.api.pojo.vo.BlogsVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */

@Repository
public interface BlogsMapper extends BaseMapper<Blog> {

    /**
     * 分页查询博客
     * @param page 查询参数
     * @param blogsQuery 博客表单
     * @return 查询结果
     */
    IPage<BlogListVO> blogsPage(Page<BlogListVO> page, @Param("blogsQuery") BlogsQuery blogsQuery);

    /**
     * 模糊搜索博客
     * @param page 分页参数
     * @param blogFuzzySearchQuery 搜索参数
     * @return 查询结果
     */
    IPage<BlogListVO> fuzzySearchPage(Page<BlogListVO> page, @Param("blogFuzzySearchQuery") BlogFuzzySearchQuery blogFuzzySearchQuery);
}
