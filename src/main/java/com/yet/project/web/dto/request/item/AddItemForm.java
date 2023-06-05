package com.yet.project.web.dto.request.item;

import lombok.Data;
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
    @NotEmpty(message = "{NotEmpty.price}")
    private Long price;

    @NotNull(message = "{NotNull.quantity}")
    @NotEmpty(message = "{NotEmpty.quantity}")
    private Long quantity;

    @NotNull(message = "{NotNull.brandId}")
    @NotEmpty(message = "{NotEmpty.brandId}")
    private Long brandId;

    @NotNull(message = "{NotNull.categoryId}")
    @NotEmpty(message = "{NotEmpty.categoryId}")
    private Long categoryId;

    @NotNull(message = "{NotNull.subcategoryId}")
    @NotEmpty(message = "{NotEmpty.subcategoryId}")
    private Long subcategoryId;

    private List<MultipartFile> images;
}
