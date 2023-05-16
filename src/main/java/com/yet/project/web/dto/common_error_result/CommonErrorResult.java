package com.yet.project.web.dto.common_error_result;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonErrorResult {
    String error;
    Integer code;
    String message;
}
