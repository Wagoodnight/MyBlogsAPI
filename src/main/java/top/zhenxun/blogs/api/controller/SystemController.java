package top.zhenxun.blogs.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhenxun.blogs.api.common.Response;
import top.zhenxun.blogs.api.pojo.form.SystemInfoUpdateForm;
import top.zhenxun.blogs.api.pojo.vo.SystemInfoVO;
import top.zhenxun.blogs.api.service.impl.SystemServiceImpl;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Api(tags = "系统模块接口")
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemServiceImpl systemService;

    @ApiOperation("获取系统信息")
    @GetMapping("/info")
    public Response<SystemInfoVO> getSystemInfo() {
        return Response.success(systemService.getSystemInfo());
    }

    @ApiOperation("更新系统信息")
    @PutMapping("/update")
    public Response<?> updateSystemInfo(@RequestBody SystemInfoUpdateForm systemInfoUpdateForm) {
        systemService.updateSystemInfo(systemInfoUpdateForm);
        return Response.success();
    }
}
