package top.zhenxun.blogs.api.pojo.vo;

import lombok.Data;
import top.zhenxun.blogs.api.pojo.Role;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class UserVO {

    private String userId;

    private String nickName;

    private String email;

    private Role role;

    private Long lastLoginTime;
}
