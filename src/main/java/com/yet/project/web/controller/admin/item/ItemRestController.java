package com.yet.project.web.controller.admin.item;

import com.yet.project.domain.item.Item;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.exception.admin.item.ItemEmptyException;
import com.yet.project.web.exception.admin.item.ItemMisMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        List<ItemJoined> ItemList = itemService.getItemsByCondition(id, quantity1, quantity2, price1, price2, brandId, categoryId, subcategoryId);
        return ItemList;
    }

    @PostMapping("/delete")
    ResponseEntity deleteItems(@RequestParam("itemIdList[]") List<Long> itemIdList) {
        //number format exception 예외처리 하기 O

        //잘 못된 key로 왔을때 예외 처리 하기 O

        //상품이 DB에 없을때 NullPointerException
        log.info("itemIdList {} ", itemIdList);
        if (itemIdList == null || itemIdList.size() == 0) {
            throw new ItemEmptyException();
        }

        for (Long itemId : itemIdList) {
            if (!itemService.removeItemByItemId(itemId)) {
                throw new ItemMisMatchException(String.valueOf(itemId));
            }
        }

        return ResponseEntity.ok().build();
    }
}
