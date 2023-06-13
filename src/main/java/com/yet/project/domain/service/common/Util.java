package com.yet.project.domain.service.common;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

public interface Util {
    boolean isKorean(String korean);
    boolean isEnglish(String english);
    ResponseEntity<UrlResource> getFileResponse(String fileName, String storedName) throws MalformedURLException;
}
