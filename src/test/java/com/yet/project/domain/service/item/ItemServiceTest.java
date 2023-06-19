package com.yet.project.domain.service.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ItemServiceTest {

    @Test
    void parseTest() {
        String originalFilename = "1234.jpg";
        String[] split = originalFilename.split("\\.");
        String fileName = split[0];
        String extend = split.length > 1 ? split[split.length - 1] : "";
        System.out.println(fileName);
        System.out.println(extend);
    }
}