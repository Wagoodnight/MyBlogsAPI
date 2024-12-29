package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.Link;
import top.zhenxun.blogs.api.pojo.vo.LinkVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

    /**
     * 分页查询友链
     * @param page 分页参数
     * @return 查询结果
     */
    IPage<LinkVO> selectLinkPage(IPage<LinkVO> page);
}
