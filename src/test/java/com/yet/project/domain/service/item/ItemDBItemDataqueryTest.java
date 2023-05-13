package com.yet.project.domain.service.item;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Item;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.service.AJ;
import com.yet.project.web.dto.item.ItemInsertForm;
import com.yet.project.web.dto.item.ItemJoined;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@MapperScan("com.yet.project.repository.mybatismapper")
@Transactional
public class ItemDBItemDataqueryTest {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemDao itemDao;

    void null11() {
//        Long categoryId = addSubcategoryForm.getCategoryId();
//        if (categoryId == null) {
//            return;
//        }
//        Subcategory subcategory = new Subcategory();
//        subcaetegory.setName(addSubcategoryForm.getName());
//        subcategory.setNameKor(addSubcategoryForm.getNameKor());
//        itemDao.saveSubcategory(subcategory);
//
//        Long subcategoryId = subcategory.getId();
//        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
//        subcategoryCategory.setCategoryId(categoryId);
//        subcategoryCategory.setSubcategoryId(subcategoryId);
//        itemDao.saveSubcategoryCategory(subcategoryCategory);
    }

    @Test
    void ItemInsertTest() {
        for (int i = 0; i < 100; i++) {
            ItemInsertForm itemInsertForm = new ItemInsertForm();
            itemInsertForm.setName("name of item" + i);
            itemInsertForm.setNameKor("아이템 이름" + i);
            itemInsertForm.setBrandId(itemMapper.selectBrandsAll().get(0).getId());
            itemInsertForm.setSubcategoryId(itemMapper.selectSubCategoryAll().get(0).getSubcategoryId());
            itemInsertForm.setPrice(123124L + i);
            itemInsertForm.setQuantity(123123L + i);
            itemService.addItem(itemInsertForm);
        }
    }

    @Test
    void ItemRemoveTest() {

    }

    @Test
    void ItemUpdateTest() {

    }

    @Test
    void ItemSelectTest() {
        List<Item> itemList = itemMapper.selectItemsLimit15();
        Map<Long, Brand> itemBrandMap = itemDao.selectBrandByItemIds(itemList);
        Map<Long, Subcategory> itemSubCategoryMap = itemDao.selectSubcategoryByItemIds(itemList);
        Map<Long, Category> subcategoryCategoryMap = itemDao.selectCategoriesBySubcategoryIds(itemSubCategoryMap.values().stream().collect(Collectors.toList()));
        Map<Long, ItemJoined> itemJoinedMap = new HashMap<>();

        for (Item item : itemList) {
            Long id = item.getId();
            ItemJoined itemJoined = new ItemJoined();
            itemJoined.setId(id);
            itemJoined.setName(item.getName());
            itemJoined.setNameKor(item.getNameKor());
            itemJoined.setPrice(item.getPrice());
            itemJoined.setQuantity(item.getQuantity());
            if (itemBrandMap.containsKey(id) && itemBrandMap.get(id) != null) {
                itemJoined.setBrandNameKor(itemBrandMap.get(id).getNameKor());
            }
            if (itemSubCategoryMap.containsKey(id) && itemSubCategoryMap.get(id) != null) {
                Subcategory subcategory = itemSubCategoryMap.get(id);
                itemJoined.setSubcategoryNameKor(subcategory.getNameKor());
                if (subcategoryCategoryMap.containsKey(subcategory.getId()) && subcategoryCategoryMap.get(subcategory.getId()) != null) {
                    itemJoined.setCategoryNameKor(subcategoryCategoryMap.get(itemSubCategoryMap.get(id).getId()).getNameKor());
                }
            }

            itemJoinedMap.put(id, itemJoined);
        }

    }

    @Test
    void ItemDeleteTest() {
        //DB에 있는 아이템 첫번째를 가져 왔을때
        List<Item> itemList = itemMapper.selectItemsLimit15();
        Item item = null;
        if (!itemList.isEmpty() && itemList.get(0) != null) {
            item = itemList.get(0);
        }
        //삭제

        if (item == null) {
            AJ.assertThat(item).isNull();
        }

        Long id = item.getId();
        itemMapper.deleteItemById(id);

        //그리고 나서 정보가 없음.
        Item findItem = itemMapper.selectItemById(id);

        AJ.assertThat(findItem).isNull();
    }

}
