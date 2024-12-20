package top.zhenxun.blogs.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@TableName("t_profile")
public class Profile {

    /**
     * 个人信息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 网站标题
     */
    private String webTitle;

    /**
     * 个性签名
     */
    private String bio;

    /**
     * 地区
     */
    private String area;

    /**
     * github地址
     */
    private String github;

    /**
     * telegram地址
     */
    private String telegram;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人简介
     */
    private String markdown;
}
