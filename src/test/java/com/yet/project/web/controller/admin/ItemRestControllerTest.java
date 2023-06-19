package com.yet.project.web.controller.admin;

import com.yet.project.domain.service.item.ItemService;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.ItemSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@MapperScan("com.yet.project.repository.mybatismapper")
@Transactional
class ItemRestControllerTest {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemService itemService;

    @Test
    void deleteItems() {
        itemMapper.deleteItemById(1L);
    }

    @Test
    void test() {
        ItemSearchDto itemSearchDto = new ItemSearchDto();
    }
}