package com.yet.project.web.dto.response.common;

import com.yet.project.service.AJ;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class APIResponseTest {

    @Test
    void quoteTest() {
        Long asdf = -1l;
        Object o = StringUtils.quoteIfString(asdf);
        String s = o.toString();
        AJ.assertThat(s).isEqualTo("-1");
    }
}