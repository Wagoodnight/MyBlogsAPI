package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.Tools;
import top.zhenxun.blogs.api.pojo.vo.ToolsVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Repository
public interface ToolsMapper extends BaseMapper<Tools> {

    /**
     * 分页查询工具
     * @param page 分页参数
     * @return 查询结果
     */
    IPage<ToolsVO> selectToolsPage(IPage<ToolsVO> page);
}
