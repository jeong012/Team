package com.fdproject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fdproject.interceptor.AdminInterceptor;
import com.fdproject.interceptor.LoggerInterceptor;
import com.fdproject.interceptor.LoginInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns() // 적용 경로
		.excludePathPatterns(); // 제외 경로
		
		registry.addInterceptor(new AdminInterceptor())
		.addPathPatterns("/admin/**")
		.excludePathPatterns();
	}

}