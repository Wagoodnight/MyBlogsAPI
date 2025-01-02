package top.zhenxun.blogs.api.pojo.vo;

import lombok.Data;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class SystemInfoVO {
    private String siteUrl;

    private String siteSecretKey;

    private String secretKey;

    private Integer enableTurnstile;

    private String icp;

    private String police;

    private String wallpaperUrl;

    private String wallpaperMd5;
}
