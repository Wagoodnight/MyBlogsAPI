package top.zhenxun.blogs.api.common;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */

public enum ResponseType {
    /**
     * 返回值枚举类
     */
    FAILED("0", "操作失败"),
    SUCCESS("200", "success"),
    UNAUTHORIZED("403", "访问未授权"),
    SERVER_ERR("500", "服务器内部错误，请联系管理员"),
    NOT_LOGIN("1000", "未登录"),
    USER_ALREADY_LOGIN("1004", "用户已登录"),
    LOGIN_FAILED("1005", "用户不存在或密码错误"),
    EMAIL_ALREADY_EXIST("1008", "邮箱已被注册"),
    TIME_OUT("1009", "操作超时"),
    NOT_FOUND("404", "请求资源不存在"),
    FILE_UPLOAD_FILED("1010", "文件上传失败"),
    FILE_CAN_NOT_BE_NULL("1011", "文件不能为空"),
    TURNSTILE_VERIFICATION_FAILED("1012", "人机验证失败"),
    OLD_PASSWORD_ERROR("1013", "旧密码错误")
    ;

    private final String code;
    private final String msg;

    ResponseType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
