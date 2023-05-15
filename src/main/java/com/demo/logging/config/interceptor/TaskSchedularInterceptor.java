package com.demo.logging.config.interceptor;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskSchedularInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
			logger.info("Inside TaskSchedularInterceptor");
			MDC.put("uuid", UUID.randomUUID().toString());
			
			return proceedingJoinPoint.proceed();
		}
		finally {
			MDC.clear();
		}
	}	
}
