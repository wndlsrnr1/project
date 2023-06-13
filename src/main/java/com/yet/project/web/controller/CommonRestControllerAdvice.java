package com.yet.project.web.controller;

import com.yet.project.web.dto.common_error_result.CommonErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
//@RestControllerAdvice(annotations = RestController.class)
public class CommonRestControllerAdvice {

    protected final MessageSource messageSource;

    public CommonRestControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonErrorResult runtimeException(Exception e) {
//        System.out.println("CommonRestControllerAdvice.runtimeException");
//        String name = e.getClass().getName();
//        Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        String message = messageSource.getMessage("error.runtime_exception", null, "runtime exception", Locale.KOREA);
//        return new CommonErrorResult(name, code, message);
//    }

    //잡히지 않는 오류
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonErrorResult uncaughtErrorException(Exception e) {
//        System.out.println("CommonRestControllerAdvice.uncaughtErrorException");
//        String name = e.getClass().getName();
//        String message = messageSource.getMessage("error.uncaught_exception", null, "uncaught exception", Locale.KOREA);
//        Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        return new CommonErrorResult(name, code, message);
//    }
}
