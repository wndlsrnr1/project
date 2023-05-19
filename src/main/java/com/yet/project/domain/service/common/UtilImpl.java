package com.yet.project.domain.service.common;

import org.springframework.stereotype.Service;

@Service
public class UtilImpl implements Util{
    @Override
    public boolean isKorean(String korean) {
        return korean.matches("^[가-힣0-9\\p{Punct}]+$");
    }

    @Override
    public boolean isEnglish(String english) {
        return english.matches("^[a-zA-Z0-9\\p{Punct}]+$");
    }
}
