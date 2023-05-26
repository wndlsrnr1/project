package com.yet.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yet.project.web.argumentresolver.LoginArgumentResolver;
import com.yet.project.web.interceptor.AddPatternConstant;
import com.yet.project.web.interceptor.ExcludePatternConstant;
import com.yet.project.web.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = AddPatternConstant.getList();
        List<String> excludePathPatterns = ExcludePatternConstant.getList();

        registry.addInterceptor(new LoginInterceptor(objectMapper))
                .order(1)
                .addPathPatterns(pathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}