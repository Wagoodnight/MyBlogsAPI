package top.zhenxun.blogs.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@TableName("t_link")
public class Link {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String url;

    private String avatar;
}
