package com.cabin.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/5/22 9:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private T data;
    private String msg;


    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(200, data, msg);
    }

    public static <T> Result<T> authFail(T data, String msg) {
        return new Result<>(401, data, msg);
    }

    public static <T> Result<T> roleFail(T data, String msg) {
        return new Result<>(403, data, msg);
    }

    /**
     * 永久重定向
     * 301 Too Many Requests
     */
    public static <T> Result<T> permanentRedirect(T data, String msg) {
        return new Result<>(301, data, msg);
    }

    /**
     * 临时重定向
     * 302 Too Many Requests
     */
    public static <T> Result<T> temporaryRedirect(T data, String msg) {
        return new Result<>(302, data, msg);
    }

    /**
     * 限流
     * 429 Too Many Requests
     */
    public static <T> Result<T> requestLimitFail(String msg) {
        return new Result<>(429, null, msg);
    }


    /**
     * 服务器错误
     * 500 Internal Server Error
     */
    public static <T> Result<T> serverFail(String msg) {
        return new Result<>(500, null, msg);
    }

    /**
     * 文件上传请求已经成功
     * 200 OK
     */
    public static <T> Result<T> uploadFileSuccess(String msg) {
        return new Result<>(200, null, msg);
    }

    /**
     * 文件正在上传,资源已创建,需要返回路径
     * 201 Created
     */
    public static <T> Result<T> uploadFileInProgress(T data, String msg) {
        return new Result<>(201, data, msg);
    }

    /**
     * 文件正在上传,资源已创建,需要返回路径
     * 201 Created
     */
    public static <T> Result<T> uploadFileInProgress(String msg) {
        return new Result<>(201, null, msg);
    }

    /**
     * 服务器错误
     * 500 Internal Server Error
     */
    public static <T> Result<T> uploadFileCancel(String msg) {
        return new Result<>(500, null, msg);
    }

    public static <T> Result<T> uploadFileExist(String msg) {
        return new Result<>(409, null, msg);
    }

    /**
     * 服务器错误
     * 507 Insufficient Storage
     */
    public static <T> Result<T> uploadFileFail(String msg) {
        return new Result<>(507, null, msg);
    }

}
