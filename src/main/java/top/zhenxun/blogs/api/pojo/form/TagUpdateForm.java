package top.zhenxun.blogs.api.pojo.form;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class TagUpdateForm {

    private Integer tagId;

    /**
     * 标签名
     */
    private String tagName;
}
