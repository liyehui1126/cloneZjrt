package com.cloneZjrt.exception;

/**
 * 逻辑异常
 * Created by Apple on 2020/2/10.
 */
public class LogicException extends RuntimeException {
    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
