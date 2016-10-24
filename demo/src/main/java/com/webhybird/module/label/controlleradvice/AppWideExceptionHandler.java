package com.webhybird.module.label.controlleradvice;

import com.webhybird.module.label.exception.DuplicateLabelException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 里面的方法对所有Controller 都有效
 * Created by wangzhongfu on 2015/10/16.
 */
@ControllerAdvice
public class AppWideExceptionHandler {

    /**
     * 统一的异常处理方法
     * @return
     */
    @ExceptionHandler(DuplicateLabelException.class)
    public String handleDuplicationException(){
        return "error/duplicate";
    }

}
