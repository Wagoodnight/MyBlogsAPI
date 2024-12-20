package top.zhenxun.blogs.api.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class BlogsVO {
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

    /**
     * 博客是否置顶
     */
    private Integer isTop;

    /**
     * 博客是否被删除
     */
    private Integer isDelete;

    /**
     * 博客创建时间
     */
    private Long createTime;

    /**
     * 博客更新时间
     */
    private Long updateTime;

    /**
     * 博客发布时间
     */
    private Long publishTime;
}
