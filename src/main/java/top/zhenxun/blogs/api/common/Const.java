package top.zhenxun.blogs.api.common;


import java.time.format.DateTimeFormatter;

/**
 * @author li
 **/
public class Const {

    /**
     * 创建token的秘钥
     */
    public static final String TOKEN_SECRET = "ZY78C6Sd3v";

    /**
     * 创建刷新token的秘钥
     */
    public static final String REFRESH_TOKEN_SECRET = "ZY78C6Sd3v";

    /**
     * 后台过期时间
     */
    public static final long ADMIN_EXPIRE_TIME = 2L * 3600L * 1000L;

    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 文件路径
     */
    public static final String FILE_FOLDER_FILE = "/file/";

    public static final Integer PROFILE_ID = 1;

    public static final String[] IMAGE_SUFFIX_LIST = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

    /**
     * 一秒钟最大请求次数
     */
    public static final Integer MAX_REQUEST_PER_SECOND = 30;

    /**
     * 临时封禁时间
     */
    public static final Integer REQUEST_LIMIT_BLOCK_PERIOD = 10 * 60;

    /**
     * Fail2Ban的发现时间
     */
    public static final Integer FAIL2BAN_FIND_TIME = 60 * 60;

    /**
     * Fail2Ban的封禁时间
     */
    public static final Long FAIL2BAN_BAN_TIME = 2L * 24L * 60L * 60L;

    /**
     * Fail2Ban的最大尝试次数
     */
    public static final Integer FAIL2BAN_MAX_TRY = 3;

    /**
     * 阿里云 CDN 的自定义请求头
     */
    public static final String ALIYUN_CDN_HEADER = "X-Individual-Host";
}
