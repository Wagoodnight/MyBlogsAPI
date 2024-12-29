package top.zhenxun.blogs.api.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogsQuery extends Pages {

    /**
     * 博客标签
     */
    private Integer tags;

    /**
     * 博客状态
     */
    private Integer status;

    /**
     * 博客是否被删除
     */
    private Integer isDelete;
}
