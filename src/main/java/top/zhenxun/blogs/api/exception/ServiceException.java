package top.zhenxun.blogs.api.exception;

import top.zhenxun.blogs.api.common.ResponseType;


/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(ResponseType responseType) {
        super(responseType.getMsg());
        this.code = responseType.getCode();
    }

    public String getCode() {
        return this.code;
    }
}
