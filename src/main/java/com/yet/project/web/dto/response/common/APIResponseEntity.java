package com.yet.project.web.dto.response.common;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Builder
public class APIResponseEntity extends ResponseEntity {
    Object body;
    HttpStatus httpStatus;

    public APIResponseEntity(HttpStatus status) {
        super(status);
    }

    public APIResponseEntity(Object body, HttpStatus status) {
        super(body, status);
    }

    public APIResponseEntity(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public APIResponseEntity(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }

    public APIResponseEntity(Object body, MultiValueMap headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    public static <X> APIResponseEntity success(X data) {
        return APIResponseEntity.builder().body(APIResponse.success(data)).httpStatus(HttpStatus.OK).build();
    }

    public static APIResponseEntity bindingError(BindingResult bindingResult) {
        return APIResponseEntity.builder().body(APIResponse.bindingError(bindingResult)).httpStatus(HttpStatus.OK).build();
    }


}

