package com.yet.project.web.controller.admin.item;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.domain.service.common.Util;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.*;
import com.yet.project.web.dto.request.item.AddEventForm;
import com.yet.project.web.dto.request.item.AddItemForm;
import com.yet.project.web.dto.response.common.APIResponseEntity;
import com.yet.project.web.dto.response.item.EventResponse;
import com.yet.project.web.dto.response.item.ImageList;
import com.yet.project.web.exception.admin.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
@Slf4j
public class ItemRestController {

    @Value("${file.dir}")
    String fileDir;

    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final Util util;

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

    @PostMapping(value = "/delete", consumes = "application/json")
    ResponseEntity deleteItems(@RequestBody List<Long> itemIdList) {
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
    public ResponseEntity addItem(@Validated @NotNull(message = "{NotNull}") AddItemForm addItemForm, BindingResult bindingResult) throws IOException {
        log.info("bindingResult {}", bindingResult);
        if (bindingResult.hasErrors()) {
            return APIResponseEntity.bindingError(bindingResult);
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
        Long itemId = itemService.addItem(addItemForm);

        if (addItemForm.getImages() != null && !addItemForm.getImages().isEmpty()) {
            List<MultipartFile> images = addItemForm.getImages();
            //파일 저장
            Map<String, String> storedResult = itemService.storeImages(images, fileDir);

            //DB에 정보 저장
            itemService.saveImageInfoList(itemId, storedResult);
        }

        if (itemId == null) {
            return ResponseEntity.accepted().build();
        }

        //200
        return APIResponseEntity.success("");
    }


    @PostMapping("/edit")
    public ResponseEntity editItem(@Validated EditItemForm editItemForm, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return APIResponseEntity.bindingError(bindingResult);
        }

        itemService.editForJoinedItemEditFormByEditForm(editItemForm);

        if (editItemForm.getImages() != null && !editItemForm.getImages().isEmpty()) {
            List<MultipartFile> images = editItemForm.getImages();
            Map<String, String> storedResult = itemService.storeImages(images, fileDir);
            itemService.saveImageInfoList(editItemForm.getId(), storedResult);
        }

        //돌아 가는 지 확인해보기
        if (editItemForm.getDeleteImages() != null && !editItemForm.getDeleteImages().isEmpty()) {
            List<String> deleteImages = editItemForm.getDeleteImages();
            itemService.removeImagesByUUID(deleteImages, fileDir);
            List<Image> images = itemService.findImagesByUuids(deleteImages);
            itemService.removeImagesInfoList(editItemForm.getId(), images);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{itemId}")
    public EditItemForm getItem(@PathVariable Long itemId) {
        log.info("itemId {}", itemId);
        EditItemForm item = itemService.getJoinedForEditFormByItemId(itemId);
        return item;
    }

    @GetMapping("/images/{fileName}")
    @ResponseBody
    public ResponseEntity<UrlResource> downloadImage(@PathVariable("fileName") String uuid) throws MalformedURLException {
        Image image = itemService.getImageByUuid(uuid);
        String fileName = image.getName() + "." + image.getExtention();
        ResponseEntity<UrlResource> fileResponse = util.getFileResponse(fileName, uuid);
        return fileResponse;
    }

    @GetMapping("/attaches/{itemId}")
    public APIResponseEntity responseImageIdList(@PathVariable Long itemId) {
        ImageList imageList = itemService.getImageIdListByItemId(itemId);
        return APIResponseEntity.success(imageList);
    }


    @PostMapping("/event")
    public ResponseEntity addNewEvent(@Validated @RequestBody AddEventForm addEventForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return APIResponseEntity.bindingError(bindingResult);
        }

        //서비스 오류
        itemService.addEventFormErrorCheck(addEventForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return APIResponseEntity.bindingError(bindingResult);
        }

        itemService.addEvent(addEventForm);

        log.info("addEventForm {}", addEventForm);
        return APIResponseEntity.ok().build();
    }

    @GetMapping("/events")
    public ResponseEntity requestEventItems(@RequestParam(value = "outdated", defaultValue = "false") Boolean outdated) {
        List<EventResponse> eventResponse = itemService.getEventItems(outdated);
        log.info("eventResponse {} ", eventResponse);
        return APIResponseEntity.success(eventResponse);
    }
}
