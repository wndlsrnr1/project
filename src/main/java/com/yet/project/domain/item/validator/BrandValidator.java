package com.yet.project.domain.item.validator;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class BrandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Brand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof Brand)) {
            return;
        }
        Brand brand = (Brand) target;
        //빈값일때
        if (brand.getName() == null || brand.getName().isEmpty()) {
            errors.rejectValue("name", "notNull", "값을 넣어서 다시 입력해주세요");
        }

        if (brand.getNameKor() == null || brand.getNameKor().isEmpty()) {
            errors.rejectValue("nameKor", "notNull", "값을 넣어서 다시 입력해주세요");
        }
    }
}
