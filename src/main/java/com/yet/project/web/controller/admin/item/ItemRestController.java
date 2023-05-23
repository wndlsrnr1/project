package com.yet.project.web.controller.admin.item;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.*;
import com.yet.project.web.exception.admin.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.jsse.PEMFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
@Slf4j
public class ItemRestController {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @GetMapping
    ItemPaging firstPage(
            ItemSearchDto itemSearchDto
    ) {
        ItemPaging itemPaging = new ItemPaging();
        List<ItemJoined> itemList = itemService.getItemsByCondition(itemSearchDto);
        Long total = itemService.getTotalByCondition(itemSearchDto);
        itemPaging.setItemList(itemList);
        itemPaging.setTotal(total);
        return itemPaging;
    }

    @PostMapping
    ItemPaging getItemsByValues(
            ItemSearchDto itemSearchDto
    ) {
        log.info("itemSearchDto {}", itemSearchDto);
        ItemPaging itemPaging = new ItemPaging();
        ItemSearchDto filteredItemSearchDto = itemService.filterValue(itemSearchDto);
        Long total = itemService.getTotalByCondition(filteredItemSearchDto);
        List<ItemJoined> itemList = itemService.getItemsByCondition(filteredItemSearchDto);
        log.info("total {}", total);
        itemPaging.setItemList(itemList);
        itemPaging.setTotal(total);
        log.info("itemList {}", itemList);
        return itemPaging;
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

    @GetMapping("/get/brands")
    public List<Brand> getItems() {
        List<Brand> brandListAll = itemService.getBrandListAll();

        if (brandListAll == null || brandListAll.isEmpty()) {
            throw new NoBrandListException();
        }

        return brandListAll;
    }

    @GetMapping("/get/categories")
    public List<Category> getCategories() {
        List<Category> categoryList = itemService.getCategoryListAll();

        if (categoryList == null || categoryList.isEmpty()) {
            throw new NoCategoryListException();
        }

        return categoryList;
    }

    @GetMapping("/get/subcategory/{categoryId}")
    public SubcategoryDto getSubcategoriesByCategoryId(@PathVariable Long categoryId) {

        List<Subcategory> subcategoryDtoList = itemService.getSubcategoryListByCategoryId(categoryId);

        if (subcategoryDtoList == null || subcategoryDtoList.isEmpty()) {
            throw new NoSubCategoryListException();
        }

        SubcategoryDto subcategoryDto = new SubcategoryDto();
        subcategoryDto.setSubcategoryList(subcategoryDtoList);
        subcategoryDto.setCategoryId(categoryId);

        return subcategoryDto;
    }

    @PostMapping("/add")
    public ResponseEntity addItem(AddItemForm addItemForm) {
        if (addItemForm == null) {
            throw new IllegalArgumentException();
        }

        String name = addItemForm.getName();
        String nameKor = addItemForm.getNameKor();
        Long price = addItemForm.getPrice();
        Long quantity = addItemForm.getQuantity();
        Long brandId = addItemForm.getBrandId();
        Long categoryId = addItemForm.getCategoryId();

        if (name == null || nameKor == null || price == null || quantity == null || brandId == null || categoryId == null) {
            throw new IllegalArgumentException();
        }

        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException();
        }


        //bad request
        if (!itemService.isBrand(addItemForm.getBrandId())) {
            throw new BrandMismatchException();
        }
        if (!itemService.isCategory(addItemForm.getCategoryId())) {
            throw new CategoryMismatchException();
        }
        if (addItemForm.getSubcategoryId() != null && !itemService.isSubcategory(addItemForm.getSubcategoryId())) {
            throw new SubcategoryMismatchException();
        }


        //202 옳은 요청이지만 실행되지 않음.
        Boolean added = itemService.addItem(addItemForm);
        if (!added) {
            return ResponseEntity.accepted().build();
        }

        //200
        return ResponseEntity.ok().build();
    }


//    @GetMapping
//    public List<ItemJoined> responsePageByParam(ItemSearchDto itemSearchDto) {
//        List<ItemJoined> itemList = itemService.getItemsByCondition(itemSearchDto);
//        return itemList;
//    }


}
