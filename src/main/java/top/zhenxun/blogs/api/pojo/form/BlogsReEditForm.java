package top.zhenxun.blogs.api.pojo.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogsReEditForm {


    /**
     * 博客id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
}
