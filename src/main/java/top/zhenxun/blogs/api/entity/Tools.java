package top.zhenxun.blogs.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@TableName("t_tools")
public class Tools {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String url;

    private String icon;
}
