package com.yet.project.web.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AddPatternConstant {
    ADMIN("/admin/**"),
    ;

    private final String uri;

    AddPatternConstant(String uri) {
        this.uri = uri;
    }

    public static AddPatternConstant getInstance() {
        return ADMIN;
    }

    public String getUri() {
        return uri;
    }

    public static List<String> getList() {
        return Arrays.stream(values()).map(AddPatternConstant::getUri).collect(Collectors.toList());
    }
}
