package top.zhenxun.blogs.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhenxun.blogs.api.common.Response;
import top.zhenxun.blogs.api.pojo.form.LinkAddForm;
import top.zhenxun.blogs.api.pojo.form.ToolsAddForm;
import top.zhenxun.blogs.api.pojo.query.Pages;
import top.zhenxun.blogs.api.service.impl.MiscServiceImpl;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Api(tags = "杂项模块接口")
@RestController
@RequestMapping("/misc")
public class MiscController {

    @Autowired
    private MiscServiceImpl miscService;

    @ApiOperation("新增友链")
    @PostMapping("/addLink")
    public Response<?> addLink(@RequestBody LinkAddForm linkAddForm) {
        miscService.addLink(linkAddForm);
        return Response.success();
    }

    @ApiOperation("新增工具")
    @PostMapping("/addTools")
    public Response<?> addTools(@RequestBody ToolsAddForm toolsAddForm) {
        miscService.addTools(toolsAddForm);
        return Response.success();
    }

    @ApiOperation("删除友链")
    @DeleteMapping ("/deleteLink/{id}")
    public Response<?> deleteLink(@PathVariable Long id) {
        miscService.deleteLink(id);
        return Response.success();
    }

    @ApiOperation("删除工具")
    @DeleteMapping("/deleteTools/{id}")
    public Response<?> deleteTools(@PathVariable Long id) {
        miscService.deleteTools(id);
        return Response.success();
    }

    @ApiOperation("分页查询友链")
    @PostMapping("/selectLinkByPage")
    public Response<?> selectLinkByPage(@RequestBody Pages pages) {
        return Response.success(miscService.selectLinkByPage(pages));
    }

    @ApiOperation("分页查询工具")
    @PostMapping("/selectToolsByPage")
    public Response<?> selectToolsByPage(@RequestBody Pages pages) {
        return Response.success(miscService.selectToolsByPage(pages));
    }
}
