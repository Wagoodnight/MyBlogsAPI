package top.zhenxun.blogs.api.pojo.form;


import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class BlogsAddFrom {

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客摘要
     */
    private String summary;

    /**
     * 博客封面图
     */
    private String coverImage;

    /**
     * 博客标签
     */
    private Integer tags;

    /**
     * 博客状态
     */
    private Integer status;

    /**
     * 博客是否置顶
     */
    private Integer isTop;

}
