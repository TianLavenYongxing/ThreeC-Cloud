package com.threec.auth.security.handler;

import com.threec.auth.security.enums.ResultEnums;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Class GlobalExceptionHandler.
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // todo 进一步完善
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> handleConversionProblem(HttpMessageConversionException ex) {
        // 创建一个错误详情对象，返回给客户端
        Map<String, Object> response = new HashMap<>();
        response.put("code", ResultEnums.INVALID_REQUEST_DATA.getCode());
        response.put("message", ResultEnums.INVALID_REQUEST_DATA.getMsg());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
