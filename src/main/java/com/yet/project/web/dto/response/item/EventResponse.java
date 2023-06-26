package com.yet.project.web.dto.response.item;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventResponse {
    private Long id;
    private Long itemId;
    private Long imageId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private String nameKor;
    private String name;
}
