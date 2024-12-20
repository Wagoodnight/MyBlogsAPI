package top.zhenxun.blogs.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.zhenxun.blogs.api.pojo.Role;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@TableName("t_user")
public class User {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;

    private String nickName;

    private String email;

    private String password;

    private Role role;

    private Long lastLoginTime;
}
