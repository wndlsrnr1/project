package com.yet.project.web.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class APIResponse<T> {
    private T data;
    private Map<String, String> fieldErrors;
    private List<String> globalErrors;

    public static <T> APIResponse<T> success(T data) {
        return APIResponse.<T>builder().data(data).build();
    }

    public static APIResponse<Map<String, String>> bindingError(BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        Map<String, String> rejectedValues = new HashMap<>();
        Map<String, String> errorMessages = new HashMap<>();


        for (FieldError fieldError : fieldErrorList) {

            String field = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();
            String defaultMessage = fieldError.getDefaultMessage();

            //set rejected Value
            if (rejectedValue == null) {
                rejectedValues.put(field, "");
            } else {
                String message = StringUtils.quoteIfString(rejectedValue).toString();
                rejectedValues.put(field, message);
            }

            //set Messages
            errorMessages.put(field, defaultMessage);
        }

        APIResponseBuilder<Map<String, String>> builder = APIResponse.<Map<String, String>>builder()
                .data(rejectedValues)
                .fieldErrors(errorMessages);

        if (!bindingResult.getGlobalErrors().isEmpty()) {
            List<String> list = new ArrayList<>();
            for (ObjectError globalError : bindingResult.getGlobalErrors()) {
                String message = globalError.getDefaultMessage();
                list.add(message);
            }

            return builder.globalErrors(list).build();
        }

        return builder.build();
    }

    public static APIResponse<Exception> handleExceptionErrors(Exception ex) {
        return null;
    }
}
