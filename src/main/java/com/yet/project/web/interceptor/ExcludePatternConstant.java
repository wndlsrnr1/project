package com.yet.project.web.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ExcludePatternConstant {
    CSS("/css/**"),
    ICON("/*.ico"),
    ERROR("/error"),
    BOOTSTRAP("/bootstrap/**"),
    IMAGES("/images/**"),
    LOGIN("/login/**"),
    LEST("/");

    private final String uri;

    ExcludePatternConstant(String uri) {
        this.uri = uri;
    }

    public String value() {
        return uri;
    }

    public static List<String> getList() {
        return Arrays.stream(values()).map(ExcludePatternConstant::value).collect(Collectors.toList());
    }
}
