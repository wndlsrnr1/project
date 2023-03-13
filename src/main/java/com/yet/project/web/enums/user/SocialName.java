package com.yet.project.web.enums.user;

public enum SocialName {
    KAKAO("kakao"), ;

    private String name;

    SocialName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
