package com.yet.project.web.dto.request.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddEventForm {
    @Null
    private Long id;

    @NotNull(message = "{NotNull.itemId}")
    @JsonProperty("item_id")
    private Long itemId;

    @NotNull(message = "{NotNull.startDate}")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @NotNull(message = "{NotNull.endDate}")
    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("image_id")
    private Long imageId;
}
