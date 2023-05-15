package com.demo.logging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.logging.config.interceptor.MDCInterceptor;

@Component
public class InterceptorRegistrationWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	MDCInterceptor mdcInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mdcInterceptor);
	}

}
