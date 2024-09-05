package com.example.project3.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                // 클래스패스에서 정적 자원 검색
                .addResourceLocations("classpath:/static/img/")
                // 사용자 지정 폴더에서 정적 자원 검색
                .addResourceLocations("file:///home/ubuntu/project/src/main/resources/static/img/");
    }
}
