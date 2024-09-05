package com.example.project3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

@SpringBootApplication
public class Project3Application {

    public static void main(String[] args) {
        try {
            // 외부 파일 시스템 경로를 지정하여 Resource 객체를 생성합니다.
            Resource[] resources = ResourcePatternUtils
                    .getResourcePatternResolver(new DefaultResourceLoader())
                    .getResources("file:///home/ubuntu/project/src/main/resources/static/img/*");

            // 찾은 리소스를 출력합니다.
            for (Resource resource : resources) {
                System.out.println("Found resource: " + resource.getFilename());
            }

            SpringApplication.run(Project3Application.class, args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
