package com.yet.project.domain.service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Service
public class UtilImpl implements Util{
    @Value("${file.dir}")
    String fileDir;

    @Override
    public boolean isKorean(String korean) {
        return korean.matches("^[가-힣0-9\\p{Punct}]+$");
    }

    @Override
    public boolean isEnglish(String english) {
        return english.matches("^[a-zA-Z0-9\\p{Punct}]+$");
    }

    @Override
    public ResponseEntity<UrlResource> getFileResponse(String fileName, String storedName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileDir + storedName);
        String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }
}
