package com.yet.project.web.dto.item.error_result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemErrorDto {
    String error;
    Integer code;
    String message;
}
