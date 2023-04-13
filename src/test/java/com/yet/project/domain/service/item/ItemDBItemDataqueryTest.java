package com.yet.project.domain.service.item;

import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.ItemInsertForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@MapperScan("com.yet.project.repository.mybatismapper")
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
//        subcategory.setName(addSubcategoryForm.getName());
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
        ItemInsertForm itemInsertForm = new ItemInsertForm();
        itemInsertForm.setName("name of item");
        itemInsertForm.setNameKor("아이템 이름");
        itemInsertForm.setBrandId(itemMapper.selectBrandsAll().get(0).getId());
        itemInsertForm.setSubcategoryId(itemMapper.selectSubCategoryAll().get(0).getSubcategoryId());
        itemInsertForm.setPrice(123124L);
        itemInsertForm.setQuantity(123123L);
        itemService.addItem(itemInsertForm);
    }

    @Test
    void ItemRemoveTest() {

    }

    @Test
    void ItemUpdateTest() {

    }

    @Test
    void ItemSelectTest() {

    }
}
