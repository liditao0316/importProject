package com.system.instaKill.exception;

import com.system.instaKill.vo.ResponseBean;
import com.system.instaKill.vo.ResponseBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 描述：全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException)e;
            return ResponseBean.error(ex.getResponseBeanEnum());
        }else if(e instanceof BindException){
            BindException ex = (BindException)e;
            ResponseBean error = ResponseBean.error(ResponseBeanEnum.BIND_ERROR);
            error.setMessage("参数校验异常："+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return error;
        }
        return ResponseBean.error(ResponseBeanEnum.ERROR);
    }
}
