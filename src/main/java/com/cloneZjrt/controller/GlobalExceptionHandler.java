package com.cloneZjrt.controller;

import com.cloneZjrt.exception.BusinessException;
import com.cloneZjrt.result.InfoResult;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * controller增强器
 * Created by Apple on 2020/2/21.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);
    /**
     * 业务异常统一处理
     * @param req
     * @param bx
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public InfoResult notFoundErrorHandler(HttpServletRequest req, BusinessException bx) {
        LOG.error(bx);
        InfoResult info = new InfoResult();
        info.fail();
        //TODO 扩展错误编码
        info.setState("50001");
        info.setMsg(bx.getMessage());
        return info;
    }

    /**
     * 位置异常统一处理
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public InfoResult defaultErrorHandler(HttpServletRequest req, RuntimeException ex) {
        // 其他异常处理逻辑...
        LOG.error(ex);
        InfoResult info = new InfoResult();
        info.fail();
        info.setMsg(ex.getMessage());
        return info;
    }
}
