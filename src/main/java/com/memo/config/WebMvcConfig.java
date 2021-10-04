package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.Interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")		// 		/** 손주를 포함한 모든 디렉토리 확인~~!~!
		.excludePathPatterns("/user/sign_out", "/static/**", "/error");  // 인터셉터를 안태울 path 설정
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")  //  http://localhost/images/s1727060_1633346649040/dodo2.jpg
		.addResourceLocations("file:///D:\\박경완\\6_spring_project\\ex\\memo_workspace\\images/"); // 실제 파일 저장 위치		
	}								  

}
