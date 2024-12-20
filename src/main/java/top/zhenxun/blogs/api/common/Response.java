package top.zhenxun.blogs.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author li
 **/
@Data
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private T data;

    public Response() {
        super();
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(String message, T data) {
        ResponseType type = ResponseType.SUCCESS;
        return new Response<>(type.getCode(), message, data);
    }

    public static <T> Response<T> success(T data) {
        ResponseType type = ResponseType.SUCCESS;
        return new Response<>(type.getCode(), type.getMsg(), data);
    }

    public static <T> Response<T> success() {
        ResponseType type = ResponseType.SUCCESS;
        return new Response<>(type.getCode(), type.getMsg(), null);
    }

    public static <T> Response<T> failed(String message, T data) {
        ResponseType type = ResponseType.FAILED;
        return new Response<>(type.getCode(), message, data);
    }

    public static <T> Response<T> failed(String message) {
        ResponseType type = ResponseType.FAILED;
        return new Response<>(type.getCode(), message);
    }

    public static <T> Response<T> failed() {
        ResponseType type = ResponseType.FAILED;
        return new Response<>(type.getCode(), type.getMsg());
    }

    public static <T> Response<T> failed(ResponseType type) {
        return new Response<>(type.getCode(), type.getMsg());
    }

    public static <T> Response<T> failed(ResponseType type, T data) {
        return new Response<>(type.getCode(), type.getMsg(), data);
    }
}