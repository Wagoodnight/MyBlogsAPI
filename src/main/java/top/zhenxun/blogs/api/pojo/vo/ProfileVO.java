package top.zhenxun.blogs.api.pojo.vo;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class ProfileVO {
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
