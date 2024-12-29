package top.zhenxun.blogs.api.pojo.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogFuzzySearchQuery extends Pages {

    /**
     * 搜索关键字
     */
    private String searchKey;

    /**
     * 搜索类型
     * 1: 标题
     * 2: 标题 + 内容
     */
    private Integer searchType;
}
