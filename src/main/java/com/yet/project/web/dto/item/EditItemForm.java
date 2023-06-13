package com.yet.project.web.dto.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
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

    @NotNull(message = "{NotEmpty.price}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "1 이상이어야합니다.")
    private Long price;

    @NotNull(message = "{NotEmpty.quantity}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "1 이상 이어야합니다.")
    private Long quantity;

    @NotNull(message = "{NotEmpty.quantity}")
    private Long brandId;

    @NotNull(message = "{NotEmpty.categoryId}")
    private Long categoryId;

    @NotNull(message = "{NotEmpty.subCategoryId}")
    private Long subcategoryId;

    private List<String> deleteImages;

    private List<MultipartFile> images;
}
