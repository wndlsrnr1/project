package com.yet.project.web.dto.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EditItemForm {
    @NotNull(message = "{NotNull.id}")
    private Long id;

    @NotEmpty(message = "{NotEmpty.name}")
    private String name;

    @NotEmpty(message = "{NotEmpty.nameKor}")
    private String nameKor;

    @NotNull(message = "{NotNull.price}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "{Range.price}")
    private Long price;

    @NotNull(message = "{NotNull.quantity}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "{Range.quantity}")
    private Long quantity;

    @NotNull(message = "{NotNull.quantity}")
    private Long brandId;

    @NotNull(message = "{NotNull.categoryId}")
    private Long categoryId;

    @NotNull(message = "{NotNull.subCategoryId}")
    private Long subcategoryId;

    private List<String> deleteImages;

    private List<MultipartFile> images;
}
