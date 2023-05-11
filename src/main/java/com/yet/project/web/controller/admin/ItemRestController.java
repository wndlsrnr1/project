package com.yet.project.web.controller.admin;

import com.yet.project.domain.service.item.ItemService;
import com.yet.project.web.dto.item.ItemJoined;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
@Slf4j
public class ItemRestController {

    private final ItemService itemService;

    @GetMapping
    List<ItemJoined> getItemsByValues(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long quantity1,
            @RequestParam(required = false) Long quantity2,
            @RequestParam(required = false) Long price1,
            @RequestParam(required = false) Long price2,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long subcategoryId
    ) {
        log.info("getItemsByValues getItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValuesgetItemsByValues");
        List<ItemJoined> ItemList = itemService.getItemsByCondition(id, quantity1, quantity2, price1, price2, brandId, categoryId, subcategoryId);
        return ItemList;
    }
}
