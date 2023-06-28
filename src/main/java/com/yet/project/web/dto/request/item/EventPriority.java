package com.yet.project.web.dto.request.item;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventPriority {
    @NotNull
    private Long id;
    @NotNull
    private Integer priority;
}
