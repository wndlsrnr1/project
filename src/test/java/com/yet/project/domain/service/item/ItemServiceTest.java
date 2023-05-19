package com.yet.project.domain.service.item;

import com.yet.dbtest.SpringDataBaseTest;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.repository.mybatismapper.user.UserMapper;
import com.yet.project.web.dto.item.AddSubcategoryForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringDataBaseTest
class ItemServiceTest {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemService itemService;


}