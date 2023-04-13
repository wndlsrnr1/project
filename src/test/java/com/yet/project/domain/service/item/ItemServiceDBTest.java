package com.yet.project.domain.service.item;

import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.SubcategoryCategory;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.service.AJ;
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

        /*
        for (SubcategoryCategory subcategoryCategory : subcategoryCategoryList) {
            Long key = subcategoryCategory.getCategoryId();
            Long value = subcategoryCategory.getSubcategoryId();
            if (result.containsKey(key)) {
                List<Long> list = result.get(key);
                list.add(value);
                continue;
            }
            result.put(key, new LinkedList<>());
            List<Long> list = result.get(key);
            list.add(value);
        }
         */
//        subcategoryCategoryList.forEach(
//            elem -> {
//                List<Long> list = result.computeIfAbsent(elem.getCategoryId(), k -> new LinkedList<>());
//                list.add(elem.getSubcategoryId());
//            }
//        );

//        for (Long key : result.keySet()) {
//            itemMapper.selectSubCategoryAllById(key);
//        }

//        System.out.println("longListMap = " + subcategoryCategoryList);
//        System.out.println("result = " + result);
    }
}