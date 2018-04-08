package com.lawu.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lawu.framework.web.exception.HeaderParamException;

/**
 * 统一异常处理
 *
 * @author Leach
 * @date 2017/3/13
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception e) throws Exception {
        logger.error("内部异常", e);
        return failServerError(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result defaultMethodArgumentErrorHandler(MethodArgumentTypeMismatchException e) throws Exception {
        logger.error("参数格式错误", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, HttpCode.SC_NOT_ACCEPTABLE, "参数格式错误", e.getMessage(), null);
    }

    @ExceptionHandler(value = HeaderParamException.class)
    @ResponseBody
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.error("非法参数", e);
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
        return response(HttpCode.SC_NOT_ACCEPTABLE, HttpCode.SC_NOT_ACCEPTABLE, "非法参数", message.toString(), null);
    }

}
