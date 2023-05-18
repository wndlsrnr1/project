package com.yet.project.domain.service.item;

import com.yet.project.domain.item.*;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.service.AJ;
import com.yet.project.web.dto.item.AddItemForm;
import com.yet.project.web.dto.item.SubCategoryJoined;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@MapperScan("com.yet.project.repository.mybatismapper")
@Transactional
class ItemServiceDBTest {

    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemService itemService;

    @Test
    void subCategoryInsertTest() {

        Category category = new Category();
        category.setName("hihi");
        category.setNameKor("하하");
        itemMapper.insertCategory(category);

        Long id = category.getId();

        Subcategory subcategory = new Subcategory();
        subcategory.setName("hihi");
        subcategory.setNameKor("히히");
        itemMapper.insertSubcategory(subcategory);

        Long id1 = subcategory.getId();
        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
        subcategoryCategory.setCategoryId(id);
        subcategoryCategory.setSubcategoryId(id1);
        itemMapper.insertSubcategoryCategory(subcategoryCategory);

        SubcategoryCategory subcategoryCategory1 = itemMapper.selectSubCategoryCategoryBySubcategoryId(id1);
        Long subcategoryId = subcategoryCategory1.getSubcategoryId();
        AJ.assertThat(subcategoryId).isEqualTo(id1);
    }

    @Test
    void subCategoryDeleteTest() {
        Category category = new Category();
        category.setName("hihi");
        category.setNameKor("하하");
        itemMapper.insertCategory(category);

        Long id = category.getId();

        Subcategory subcategory = new Subcategory();
        subcategory.setName("hihi");
        subcategory.setNameKor("히히");
        itemMapper.insertSubcategory(subcategory);

        Long id1 = subcategory.getId();
        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
        subcategoryCategory.setCategoryId(id);
        subcategoryCategory.setSubcategoryId(id1);
        itemMapper.insertSubcategoryCategory(subcategoryCategory);

        SubcategoryCategory subcategoryCategory1 = itemMapper.selectSubCategoryCategoryBySubcategoryId(id1);
        Long subcategoryId = subcategoryCategory1.getSubcategoryId();
        AJ.assertThat(subcategoryId).isEqualTo(id1);

        itemMapper.deleteSubcategoryById(subcategoryId);
        SubcategoryCategory subcategoryCategory2 = itemMapper.selectSubCategoryCategoryBySubcategoryId(subcategoryId);
        AJ.assertThat(subcategoryCategory2).isNull();
    }

    @Test
    void getAllSubcategoryTest() {
        HashMap<Long, List<Subcategory>> result = new HashMap<>();
        List<SubCategoryJoined> subCategoryJoinedList = itemMapper.selectSubCategoryAll();
        subCategoryJoinedList.forEach(elem -> {
            Long key = elem.getCategoryId();
            Subcategory subcategory = new Subcategory();
            subcategory.setId(elem.getSubcategoryId());
            subcategory.setName(elem.getName());
            subcategory.setNameKor(elem.getNameKor());

            List<Subcategory> subcategoryList = result.computeIfAbsent(key, k -> new ArrayList<>());
            subcategoryList.add(subcategory);
        });
    }

    @Test
    void selectBrandCategorySubCategoryTest() {
        List<Brand> brandList = itemMapper.selectBrandsAll();
        List<Category> categories = itemMapper.selectCategoryAll();

        if (!categories.isEmpty() && categories.get(0) != null) {
            Long categoryId = categories.get(0).getId();
            List<Subcategory> subcategoryList = itemMapper.selectSubcategoryByCategoryIdAll(categoryId);
            System.out.println("subcategoryList = " + subcategoryList);
        }

        for (Category category : categories) {
            Long categoryId = category.getId();
            List<Subcategory> subcategoryList = itemMapper.selectSubCategoryByCategoryId(categoryId);
            System.out.println("subcategoryList = " + subcategoryList);
        }
    }

    @Test
    void addItemTest() {
        AddItemForm addItemForm = new AddItemForm();

        //find info randomly
        addItemForm.setBrandId(itemMapper.selectBrandsAll().get(0).getId());
        addItemForm.setPrice(1l);
        addItemForm.setQuantity(1l);
        Long id = itemMapper.selectCategoryAll().get(0).getId();
        addItemForm.setCategoryId(id);
        addItemForm.setName("name");
        addItemForm.setNameKor("이름");
        addItemForm.setSubcategoryId(itemMapper.selectSubCategoryByCategoryId(id).get(0).getId());

        //mapper

        //item
        Item item = new Item();
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

        //check item service works
        Item item1 = itemMapper.selectItemById(item.getId());
        ItemBrand itemBrand1 = itemMapper.selectItemBrandByItemId(item.getId());
        ItemSubcategory itemSubcategory1 = itemMapper.selectItemSubcategoryByItemId(item.getId());

        AJ.assertThat(item1.getId()).isEqualTo(item.getId());
        AJ.assertThat(itemBrand1).isEqualTo(itemBrand);
        AJ.assertThat(itemSubcategory1).isEqualTo(itemSubcategory);


    }

}