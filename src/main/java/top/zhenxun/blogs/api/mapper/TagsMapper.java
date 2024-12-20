package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.Tags;
import top.zhenxun.blogs.api.pojo.vo.TagCount;

import java.util.List;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Repository
public interface TagsMapper extends BaseMapper<Tags> {

    /**
     * 查询标签
     * @return 标签列表
     */
    List<Tags> selectTags();

    /**
     * 查询标签计数
     * @return 标签计数
     */
    List<TagCount> selectTagCount();
}
