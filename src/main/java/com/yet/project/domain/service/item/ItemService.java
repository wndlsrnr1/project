package com.yet.project.domain.service.item;

import com.yet.project.domain.item.*;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.*;
import com.yet.project.web.dto.request.item.AddItemForm;
import com.yet.project.web.dto.response.item.ImageList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemService {
    private final ItemDao itemDao;
    private final ItemMapper itemMapper;

    public void addCategory(Category category) {
        itemDao.saveCategory(category);
    }


    public List<Category> getCategoryListAll() {
        return itemDao.findCategoriesAll();
    }


    public boolean removeCategoryById(Long id) {
        try {
            itemDao.removeCategoryById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public List<Brand> getBrandListAll() {
        return itemDao.findBrandsAll();
    }

    public void addBrand(Brand brand) {
        itemDao.saveBrand(brand);
    }

    public boolean removeBrandById(Long id) {
        try {
            itemDao.removeBrandById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void updateBrand(Brand brand) {
        itemDao.updateBrand(brand);
    }

    public void updateCategory(Category category) {
        itemDao.updateCategory(category);
    }


    public void addSubcategory(AddSubcategoryForm addSubcategoryForm) {
        Long categoryId = addSubcategoryForm.getCategoryId();
        if (categoryId == null) {
            return;
        }
        Subcategory subcategory = new Subcategory();
        subcategory.setName(addSubcategoryForm.getName());
        subcategory.setNameKor(addSubcategoryForm.getNameKor());
        itemDao.saveSubcategory(subcategory);

        Long subcategoryId = subcategory.getId();
        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
        subcategoryCategory.setCategoryId(categoryId);
        subcategoryCategory.setSubcategoryId(subcategoryId);
        itemDao.saveSubcategoryCategory(subcategoryCategory);
    }

    public Map<Long, List<Subcategory>> getSubcategoryAllByCategoryId() {
        HashMap<Long, List<Subcategory>> result = new HashMap<>();
        List<SubCategoryJoined> subCategoryJoinedList = itemDao.selectSubCategoryAll();
        subCategoryJoinedList.forEach(elem -> {
            Long key = elem.getCategoryId();
            Subcategory subcategory = new Subcategory();
            subcategory.setId(elem.getSubcategoryId());
            subcategory.setName(elem.getName());
            subcategory.setNameKor(elem.getNameKor());

            List<Subcategory> subcategoryList = result.computeIfAbsent(key, k -> new ArrayList<>());
            subcategoryList.add(subcategory);
        });
        return result;
    }

    public boolean removeSubcategoryById(Long id) {
        try {
            itemDao.removeSubcategoryById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void renameSubcategory(UpdateSubcategory updateSubcategoryForm) {
        Subcategory subcategory = new Subcategory();
        subcategory.setNameKor(updateSubcategoryForm.getNameKor());
        subcategory.setName(updateSubcategoryForm.getName());
        subcategory.setId(updateSubcategoryForm.getId());

        Long categoryId = updateSubcategoryForm.getCategoryId();
        itemDao.updateSubcategory(categoryId, subcategory);
    }

    public List<ItemJoined> findItemJoinedList() {
        return itemDao.findItemJoinedList();
    }

    public void addItemDpreciated(ItemInsertForm itemInsertForm) {
        //Item Insert
        Item item = new Item();
        item.setNameKor(itemInsertForm.getNameKor());
        item.setName(itemInsertForm.getName());
        item.setQuantity(itemInsertForm.getQuantity());
        item.setPrice(itemInsertForm.getPrice());
        itemDao.addItem(item);


        //itembrand insert
        ItemBrand itemBrand = new ItemBrand();
        itemBrand.setItemId(item.getId());
        itemBrand.setBrandId(itemInsertForm.getBrandId());
        itemDao.addItemBrand(itemBrand);

        //itemSubcategory Insert
        ItemSubcategory itemSubcategory = new ItemSubcategory();
        itemSubcategory.setSubcategoryId(itemInsertForm.getSubcategoryId());
        itemSubcategory.setItemId(item.getId());
        itemDao.addItemSubcategory(itemSubcategory);
    }

    public void loadAll(Model model) {
        List<Category> categoryListAll = getCategoryListAll();
        model.addAttribute("categoryList", categoryListAll);

        //BrandList from DB
        List<Brand> brandList = getBrandListAll();
        model.addAttribute("brandList", brandList);

        Map<Long, List<Subcategory>> subcategoryByCategory = getSubcategoryAllByCategoryId();
        model.addAttribute("subcategoryByCategory", subcategoryByCategory);
    }

    public List<ItemJoined> getItemsByCondition(
            ItemSearchDto itemSearchDto
    ) {

        List<ItemJoined> joinedList = itemMapper.selectItemJoinedByCondition(itemSearchDto);

        for (ItemJoined itemJoined : joinedList) {
            String brandNameKor = itemMapper.selectBrandByBrandId(itemJoined.getBrandId()).getNameKor();
            String categoryNameKor = itemMapper.selectCategoryById(itemJoined.getCategoryId()).getNameKor();
            itemJoined.setBrandNameKor(brandNameKor);
            itemJoined.setCategoryNameKor(categoryNameKor);
            if (itemJoined.getSubcategoryId() != null) {
                String subcategoryNameKor = itemMapper.selectSubcategoryById(itemJoined.getSubcategoryId()).getNameKor();
                itemJoined.setSubcategoryNameKor(subcategoryNameKor);
            }
        }

        //조건 검색
        return joinedList;
    }

    private ArrayList<ItemJoined> returnFirstPage() {
        List<Item> itemList = itemMapper.selectItemsLimit15();

        Map<Long, Brand> itemBrandMap = itemDao.selectBrandByItemIds(itemList);

        Map<Long, Subcategory> itemSubCategoryMap = itemDao.selectSubcategoryByItemIds(itemList);

        Map<Long, Category> subcategoryCategoryMap = itemDao.selectCategoriesBySubcategoryIds(itemSubCategoryMap.values().stream().collect(Collectors.toList()));

        Map<Long, ItemJoined> itemJoinedMap = new LinkedHashMap<>();

        for (Item item : itemList) {
            Long itemId = item.getId();
            ItemJoined itemJoined = new ItemJoined();
            itemJoined.setId(itemId);
            itemJoined.setName(item.getName());
            itemJoined.setNameKor(item.getNameKor());
            itemJoined.setPrice(item.getPrice());
            itemJoined.setQuantity(item.getQuantity());
            if (itemBrandMap.containsKey(itemId) && itemBrandMap.get(itemId) != null) {
                itemJoined.setBrandNameKor(itemBrandMap.get(itemId).getNameKor());
            }
            if (itemSubCategoryMap.containsKey(itemId) && itemSubCategoryMap.get(itemId) != null) {
                Subcategory subcategory = itemSubCategoryMap.get(itemId);
                itemJoined.setSubcategoryNameKor(subcategory.getNameKor());
                if (subcategoryCategoryMap.containsKey(subcategory.getId()) && subcategoryCategoryMap.get(subcategory.getId()) != null) {
                    itemJoined.setCategoryNameKor(subcategoryCategoryMap.get(itemSubCategoryMap.get(itemId).getId()).getNameKor());
                }
            }

            itemJoinedMap.put(itemId, itemJoined);
        }

        return new ArrayList<>(itemJoinedMap.values());
    }

    public ItemSearchDto filterValue(ItemSearchDto itemSearchDto) {

        ItemSearchDto createdISD = new ItemSearchDto();
        createdISD.setPerPage(15L);
        createdISD.setPage(itemSearchDto.getPage());
        createdISD.setItemName(itemSearchDto.getItemName());
        createdISD.setQuantity2(itemSearchDto.getQuantity2());
        createdISD.setQuantity1(itemSearchDto.getQuantity1());
        createdISD.setPrice1(itemSearchDto.getPrice1());
        createdISD.setPrice2(itemSearchDto.getPrice2());
        createdISD.setId(itemSearchDto.getId());
        createdISD.setBrandId1(itemSearchDto.getBrandId1());
        createdISD.setBrandId2(itemSearchDto.getBrandId2());
        createdISD.setCategoryId1(itemSearchDto.getCategoryId1());
        createdISD.setCategoryId2(itemSearchDto.getCategoryId2());
        createdISD.setSubcategoryId1(itemSearchDto.getSubcategoryId1());
        createdISD.setSubcategoryId2(itemSearchDto.getSubcategoryId2());

        if (itemSearchDto.getBrandId1() == null) {
            createdISD.setBrandId1(0L);
            createdISD.setBrandId2(Long.MAX_VALUE);
        } else {
            createdISD.setBrandId2(itemSearchDto.getBrandId1());
        }

        if (itemSearchDto.getCategoryId1() == null) {
            createdISD.setCategoryId1(0L);
            createdISD.setCategoryId2(Long.MAX_VALUE);
        } else {
            createdISD.setCategoryId2(itemSearchDto.getCategoryId1());
        }

        if (itemSearchDto.getSubcategoryId1() == null) {
            createdISD.setSubcategoryId1(0L);
            createdISD.setSubcategoryId2(Long.MAX_VALUE);
        } else {
            createdISD.setSubcategoryId2(itemSearchDto.getSubcategoryId1());
        }

        if (itemSearchDto.getItemName() == null) {
            createdISD.setItemName("");
        }

        if (itemSearchDto.getPage() == null) {
            createdISD.setPage(0L);
        } else {
            createdISD.setPage((itemSearchDto.getPage() - 1) * 15);
        }

        if (itemSearchDto.getQuantity1() == null) {
            createdISD.setQuantity1(0L);
        }

        if (itemSearchDto.getQuantity2() == null) {
            createdISD.setQuantity2(Long.MAX_VALUE);
        }

        if (itemSearchDto.getPrice1() == null) {
            createdISD.setPrice1(0L);
        }

        if (itemSearchDto.getPrice2() == null) {
            createdISD.setPrice2(Long.MAX_VALUE);
        }
        log.info("createdISD {}", createdISD);
        return createdISD;
    }

    public boolean removeItemByItemId(Long itemId) {
        return itemDao.deleteItemByItemId(itemId);
    }

    public List<Subcategory> getSubcategoryListByCategoryId(Long categoryId) {
        List<Subcategory> subcategoryList = itemDao.selectSubcategoryByCategoryId(categoryId);
        return subcategoryList;
    }

    public Long addItem(AddItemForm addItemForm) {
        Item item = new Item();
        try {
            //item
            item.setPrice(addItemForm.getPrice());
            item.setQuantity(addItemForm.getQuantity());
            item.setName(addItemForm.getName());
            item.setNameKor(addItemForm.getNameKor());
            itemMapper.insertItem(item);

            //item_brand
            ItemBrand itemBrand = new ItemBrand();
            itemBrand.setItemId(item.getId());
            itemBrand.setBrandId(addItemForm.getBrandId());
            itemMapper.insertItemBrand(itemBrand);

            //item_subcategory
            ItemSubcategory itemSubcategory = new ItemSubcategory();
            itemSubcategory.setItemId(item.getId());
            itemSubcategory.setSubcategoryId(addItemForm.getSubcategoryId());
            itemMapper.insertItemSubcategory(itemSubcategory);

            //item_category
            ItemCategory itemCategory = new ItemCategory();
            itemCategory.setItemId(item.getId());
            itemCategory.setCategoryId(addItemForm.getCategoryId());
            itemMapper.insertItemCategory(itemCategory);
        } catch (Exception e) {
            return null;
        }

        return item.getId();
    }

    public Boolean isBrand(Long brandId) {
        Brand brand = itemMapper.selectBrandByBrandId(brandId);
        return brand != null;
    }

    public Boolean isSubcategory(Long subcategoryId) {
        Subcategory subcategory = itemMapper.selectSubcategoryById(subcategoryId);
        return subcategory != null;
    }

    public Boolean isCategory(Long categoryId) {
        Category category = itemMapper.selectCategoryById(categoryId);
        return category != null;
    }

    public Long getTotalByCondition(ItemSearchDto itemSearchDto) {
        List<ItemSearchDto> itemSearchDtoList = itemMapper.countItemsByValue(itemSearchDto);
        if (itemSearchDtoList == null || itemSearchDtoList.isEmpty()) {
            return 0L;
        } else {
            return (long) itemSearchDtoList.size();
        }
    }

    public EditItemForm getJoinedForEditFormByItemId(Long itemId) {
        return itemMapper.selectForJoinedEditFormByItemId(itemId);
    }

    public void editForJoinedItemEditFormByEditForm(EditItemForm editItemForm) {
        Item item = new Item();
        item.setId(editItemForm.getId());
        item.setQuantity(editItemForm.getQuantity());
        item.setPrice(editItemForm.getPrice());
        item.setName(editItemForm.getName());
        item.setNameKor(editItemForm.getNameKor());
        itemMapper.updateItemByItem(item);

        ItemBrand itemBrand = new ItemBrand();
        itemBrand.setItemId(editItemForm.getId());
        itemBrand.setBrandId(editItemForm.getBrandId());
        itemMapper.updateItemBrandByBrandId(itemBrand);

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setItemId(editItemForm.getId());
        itemCategory.setCategoryId(editItemForm.getCategoryId());
        itemMapper.updateItemCategory(itemCategory);

        ItemSubcategory itemSubcategory = new ItemSubcategory();
        itemSubcategory.setItemId(editItemForm.getId());
        itemSubcategory.setSubcategoryId(editItemForm.getSubcategoryId());
        itemMapper.updateItemSubcategory(itemSubcategory);

    }

    public List<Image> storeImageFiles(AddItemForm addItemForm) throws IOException {
        List<Image> imageList = new ArrayList<>();
        List<MultipartFile> images = addItemForm.getImages();
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();
            image.transferTo(new File("aa"));
        }
        return imageList;
    }

    public Map<String, String> storeImages(List<MultipartFile> images, String fileDir) throws IOException {
        Map<String, String> map = new HashMap<>();
        for (MultipartFile image : images) {
            if (image.getSize() == 0) {
                continue;
            }
            String storedName = storeImage(image, fileDir);
            map.put(storedName, image.getOriginalFilename());
        }
        return map;
    }

    public String storeImage(MultipartFile file, String fileDir) throws IOException {
        String uuid = UUID.randomUUID().toString();
        file.transferTo(new File(fileDir + uuid));
        return uuid;
    }

    public void saveImageInfoList(Long itemId, Map<String, String> images) {
        for (String uuid : images.keySet()) {
            String originalFileName = images.get(uuid);
            saveImageInfo(itemId, uuid, originalFileName);
        }
    }

    public void saveImageInfo(Long itemId, String uuid, String originalFilename) {

        String[] split = originalFilename.split("\\.");
        String fileName = split[0];
        String extend = split.length > 1 ? split[split.length - 1] : "";

        Image image = new Image();
        image.setUuid(uuid);
        image.setExtention(extend);
        image.setName(fileName);

        itemMapper.insertImage(image);

        ItemImage itemImage = new ItemImage();
        itemImage.setImageId(image.getId());
        itemImage.setItemId(itemId);
        log.info("itemImage {}", itemImage);
        itemMapper.insertItemImage(itemImage);
    }

    public String getFileFillPath(String uuid, String fileDir) {
        return fileDir + uuid;
    }


    public Image getImageByItemIdAndUuid(Long itemId, String storedFileName) {
        return itemMapper.selectImageByItemIdAndUuid(itemId, storedFileName);
    }

    public Image getImageByUuid(String uuid) {
        return itemMapper.selectImageByUUid(uuid);
    }

    public ImageList getImageIdListByItemId(Long itemId) {
        List<Image> imageList = itemMapper.selectImagesByItemId(itemId);
        ImageList imageList1 = new ImageList();
        imageList1.setImageList(imageList);
        return imageList1;
    }

    public void removeImagesByUUID(List<String> deleteImages, String fileDir) {
        for (String deleteImage : deleteImages) {
            removeImageByUUID(deleteImage, fileDir);
        }
    }

    public void removeImageByUUID(String uuid, String fileDir) {
        File file = new File(fileDir + uuid);
        if (file.exists()) {
            if (!file.delete()) {
                log.error("file delete fail" + uuid);
            }
        }
    }

    public void removeImagesInfoList(Long id, List<Image> result) {
        for (Image image : result) {
            removeImageInfoList(id, image.getId());
        }
    }

    public void removeImageInfoList(Long id, Long imageId) {
        itemMapper.deleteImageByImageId(imageId);
    }

    public List<Image> findImagesByUuids(List<String> deleteImages) {
        List<Image> imageList = new ArrayList<>();
        for (String uuid : deleteImages) {
            Image image = itemMapper.selectImageByUUid(uuid);
            imageList.add(image);
        }
        return imageList;
    }
}
