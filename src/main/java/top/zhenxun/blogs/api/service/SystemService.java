package top.zhenxun.blogs.api.service;

import top.zhenxun.blogs.api.pojo.form.SystemInfoUpdateForm;
import top.zhenxun.blogs.api.pojo.vo.SystemInfoVO;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public interface SystemService {

    /**
     * 获取系统信息
     * @return 系统信息
     */
    public SystemInfoVO getSystemInfo();

    /**
     * 更新系统信息
     * @param systemInfoUpdateForm 系统信息更新表单
     */
    void updateSystemInfo(SystemInfoUpdateForm systemInfoUpdateForm);
}
