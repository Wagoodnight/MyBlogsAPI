package top.zhenxun.blogs.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
@TableName("t_system_setting")
public class SystemInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String siteUrl;

    private String secretKey;

    private String siteSecretKey;

    private Integer enableTurnstile;

    private String icp;
}
