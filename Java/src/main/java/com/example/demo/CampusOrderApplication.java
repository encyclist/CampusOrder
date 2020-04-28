package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class CampusOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusOrderApplication.class, args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() { // 设置临时路径
		// 上传地址
//        String path = "C:/Users/rex.li/IdeaProjects/blog/src/main/webapp/uploadImg/";
        String path = "F:/uploadImg/";
		// linux地址
//		String path = "/usr/local/project/img/";
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(path);
		return factory.createMultipartConfig();
	}
}
