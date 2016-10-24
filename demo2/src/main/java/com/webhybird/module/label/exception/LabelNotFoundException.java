package com.webhybird.module.label.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 在自定义异常上添加注解，使Spring mvc 在处理异常时可以指定http 状态码
 * Created by wangzhongfu on 2015/10/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Label not found")
public class LabelNotFoundException extends Exception {


}
