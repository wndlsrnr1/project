package com.yet.project.web.controller.item;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.response.common.APIResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class CommonItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping("/categories")
    public ResponseEntity requestCategoryAll() {
        List<Category> categoryListAll = itemService.getCategoryListAll();
        log.info("categoryList {}", categoryListAll);
        return APIResponseEntity.success(categoryListAll);
    }

    @GetMapping("/subcategories")
    public ResponseEntity requestSubcategoryAll() {
        Map<Long, List<Subcategory>> subcategoryAllByCategoryId = itemService.getSubcategoryAllByCategoryId();
        return APIResponseEntity.success(subcategoryAllByCategoryId);
    }

    @GetMapping("/brands/most")
    public ResponseEntity requestMostBrands() {
        List<Brand> brandListAll = itemService.getBrandListAll();
        return APIResponseEntity.success(brandListAll);
    }

}
