package com.yet.project.web.controller.admin;

import com.yet.project.domain.item.Item;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.web.dto.item.ItemJoined;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        //Number format Exception 예외처리 하기
        //잘 못된 key로 왔을때 예외 처리 하기
        //상품이 DB에 없을때 NullPointerException

        if (itemIdList == null) {
            return ResponseEntity.badRequest().build();
        }

        for (Long itemId : itemIdList) {
            try {
                itemService.removeItemByItemId(itemId);
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok().build();
    }
}
