package com.cloneZjrt.exception;

import lombok.Data;

/**
 * 业务异常
 * Created by Apple on 2020/2/10.
 */
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -2780823944154925080L;
    private Long errorCode;//错误代码
    private String msg;//错误信息

    public BusinessException(String message) {
        super(message);
        this.msg = message;
    }

    public BusinessException(String message, Long errorCode) {
        super(message);
        this.msg = message;
        this.errorCode = errorCode;
    }
}
