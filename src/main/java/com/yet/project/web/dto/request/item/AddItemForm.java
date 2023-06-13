package com.yet.project.web.dto.request.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddItemForm {
    @NotNull(message = "{NotNull.name}")
    @NotEmpty(message = "{NotEmpty.name}")
    private String name;

    @NotNull(message = "{NotNull.nameKor}")
    @NotEmpty(message = "{NotEmpty.nameKor}")
    private String nameKor;

    @NotNull(message = "{NotNull.price}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "1 이상 이어야합니다.")
    private Long price;

    @NotNull(message = "{NotNull.quantity}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "1 이상 이어야합니다.")
    private Long quantity;

    @NotNull(message = "{NotNull.brandId}")
    private Long brandId;

    @NotNull(message = "{NotNull.categoryId}")
    private Long categoryId;

    @NotNull(message = "{NotNull.subcategoryId}")
    private Long subcategoryId;

    private List<MultipartFile> images;

}
