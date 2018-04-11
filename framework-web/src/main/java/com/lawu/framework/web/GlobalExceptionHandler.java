package com.lawu.framework.web;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lawu.framework.web.exception.HeaderParamException;
import com.lawu.framework.web.exception.ParamSignException;

/**
 * 统一异常处理
 *
 * @author Leach
 * @date 2017/3/13
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(Exception e) throws Exception {
        logger.error("内部异常", e);
        return failServerError(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result defaultMethodArgumentErrorHandler(MethodArgumentTypeMismatchException e) throws Exception {
        logger.error("参数格式错误", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, HttpCode.SC_NOT_ACCEPTABLE, "参数格式错误", e.getMessage(), null);
    }

    @ExceptionHandler(value = HeaderParamException.class)
    public Result headerParamErrorHandler(HeaderParamException e) throws Exception {
        logger.error("头部参数非法", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, HttpCode.SC_NOT_ACCEPTABLE, "头部参数非法", e.getMessage(), null);
    }
    
    /**
     * 校验参数
     * @param e
     * @return
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = BindException.class)
    public Result bindExceptionHandler(BindException e){
        StringBuilder message = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                if (message.length() > 0) {
                    message.append("|");
                }
                // 收集错误信息
                message.append(objectError.getDefaultMessage());
            }
        }
        return response(HttpCode.SC_BAD_REQUEST, HttpCode.SC_BAD_REQUEST, "参数验证失败", message.toString(), null);
    }
    
    /**
     * 校验参数
     * @param e
     * @return
     * @author jiangxinjun
     * @createDate 2018年4月11日
     * @updateDate 2018年4月11日
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        StringBuilder message = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                if (message.length() > 0) {
                    message.append("|");
                }
                // 收集错误信息
                message.append(objectError.getDefaultMessage());
            }
        }
        return response(HttpCode.SC_BAD_REQUEST, HttpCode.SC_BAD_REQUEST, "参数验证失败", message.toString(), null);
    }
    
    /**
     * 校验方法级参数
     * @param e
     * @return
     * @author jiangxinjun
     * @createDate 2018年4月11日
     * @updateDate 2018年4月11日
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e){
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> constraintViolationIterator = constraintViolations.iterator();
        if (constraintViolationIterator.hasNext()) {
            ConstraintViolation<?> constraintViolation = constraintViolationIterator.next();
            if (message.length() > 0) {
                message.append("|");
            }
            // 收集错误信息
            message.append(constraintViolation.getMessage());
        }
        return response(HttpCode.SC_BAD_REQUEST, HttpCode.SC_BAD_REQUEST, "参数验证失败", message.toString(), null);
    }
    
    @ExceptionHandler(value = ParamSignException.class)
    public Result paramSignErrorHandler(ParamSignException e) throws Exception {
        logger.error("签名错误", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, BaseResultCode.FAIL, "签名错误", e.getMessage(), null);
    }
}
