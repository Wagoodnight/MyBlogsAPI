package top.zhenxun.blogs.api.pojo;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 **/
@Data
public class CurrentLoginUser {

    private String userId;

    private Role role;
}
