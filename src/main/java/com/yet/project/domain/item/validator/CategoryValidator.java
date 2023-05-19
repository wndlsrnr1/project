package com.yet.project.domain.item.validator;

import com.yet.project.domain.item.Category;
import com.yet.project.domain.service.common.Util;
import com.yet.project.domain.service.common.UtilImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Slf4j
@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("Category.class.isAssignableFrom(clazz) = {}", Category.class.isAssignableFrom(clazz));
        return Category.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof Category)) {
            return;
        }
        Category category = (Category) target;
        log.info("asdfasdf");
        //빈값일때
        if (category.getName() == null || category.getName().isEmpty()) {
            errors.rejectValue("name", "notNull", "값을 넣어서 다시 입력해주세요");
        }

        if (category.getNameKor() == null || category.getNameKor().isEmpty()) {
            errors.rejectValue("nameKor", "notNull", "값을 넣어서 다시 입력해주세요");
        }
    }
}
