package com.yet.project.web.controller.admin.item;

import com.yet.project.web.controller.CommonRestControllerAdvice;
import com.yet.project.web.dto.item.error_result.ItemErrorDto;
import com.yet.project.web.exception.admin.item.ItemEmptyException;
import com.yet.project.web.exception.admin.item.ItemMisMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@RestControllerAdvice(basePackages = "com.yet.project.web.controller.admin")
@Slf4j
public class ItemRestControllerAdvice extends CommonRestControllerAdvice {

    public ItemRestControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ItemErrorDto handleItemEmptyException(ItemEmptyException e) {
        String error = getOnlyClassName(e);
        String message = messageSource.getMessage("error.item_empty_exception", null, "item list is empty", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }

    @ExceptionHandler(ItemMisMatchException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ItemErrorDto handleItemMisMatchException(ItemMisMatchException e) {
        log.error("handleITemMisMatchExcepetion");
        String error = getOnlyClassName(e);
        String message = messageSource.getMessage("error.item_mismatch_exception", new Object[]{e.getMessage()}, "no item with corresponding id", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ItemErrorDto handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String error = getOnlyClassName(e);
        String message = messageSource.getMessage("error.missing_servlet_request_parameter_exception", null, "missing parameter", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ItemErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String error = getOnlyClassName(e);
        String message = messageSource.getMessage("error.type_mismatch_exception", null, "type mismatch exception", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }

    private String getOnlyClassName(Exception e) {
        String[] split = e.getClass().getName().split("\\.");
        return split[split.length - 1];
    }

    @ExceptionHandler({TypeMismatchException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ItemErrorDto typeMismatchException(NumberFormatException e) {
        System.out.println("ItemRestControllerAdvice.typeMismatchException");
        String error = getOnlyClassName(e);
        String message = messageSource.getMessage("error.type_mismatch_exception", null, "type mismatch exception", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }

    @ExceptionHandler({NumberFormatException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "error.number_format_exception")
    public ItemErrorDto numberFormatException(NumberFormatException e) {
        System.out.println("ItemRestControllerAdvice.numberFormatException");
        String error = e.getClass().getName();
        String message = messageSource.getMessage("error.number_format_exception", null, "number format exception", Locale.KOREA);
        Integer code = HttpStatus.BAD_REQUEST.value();
        return new ItemErrorDto(error, code, message);
    }
}
