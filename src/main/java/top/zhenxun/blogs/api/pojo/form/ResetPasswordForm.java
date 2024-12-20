package top.zhenxun.blogs.api.pojo.form;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class ResetPasswordForm {

    private String oldPassword;

    private String newPassword;
}
