package top.zhenxun.blogs.api.pojo.vo;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class LoginResultVO {

    private String userId;

    private String nickName;

    private String email;

    private Long lastLoginTime;

    private String token;
}
