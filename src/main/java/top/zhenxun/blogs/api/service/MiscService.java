package top.zhenxun.blogs.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.zhenxun.blogs.api.pojo.form.LinkAddForm;
import top.zhenxun.blogs.api.pojo.form.ToolsAddForm;
import top.zhenxun.blogs.api.pojo.query.Pages;
import top.zhenxun.blogs.api.pojo.vo.LinkVO;
import top.zhenxun.blogs.api.pojo.vo.ToolsVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public interface MiscService {

    /**
     * 新增友链
     * @param linkAddForm 友链表单
     */
    void addLink(LinkAddForm linkAddForm);

    /**
     * 新增工具
     * @param toolsAddForm 工具表单
     */
    void addTools(ToolsAddForm toolsAddForm);

    /**
     * 分页查询友链
     * @param pages 分页参数
     * @return 查询结果
     */
    IPage<LinkVO> selectLinkByPage(Pages pages);

    /**
     * 分页查询工具
     * @param pages 分页参数
     * @return 查询结果
     */
    IPage<ToolsVO> selectToolsByPage(Pages pages);

    /**
     * 删除友链
     * @param id 友链id
     */
    void deleteLink(Long id);

    /**
     * 删除工具
     * @param id 工具id
     */
    void deleteTools(Long id);
}
