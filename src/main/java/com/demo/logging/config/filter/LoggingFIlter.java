package com.demo.logging.config.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
@Order(1)
//@WebFilter( filterName = "mdcFilter", urlPatterns = { "/*" } )
public class LoggingFIlter extends OncePerRequestFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.info("Inside  Logging Filter");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			configureMDC(request);
			response.setHeader("uuid", MDC.get("uuid"));
			filterChain.doFilter(request, response);
			postLogging(response, stopWatch);
		} finally {
			MDC.clear();
		}

	}
	
	private void configureMDC(HttpServletRequest request) {
		String uuid = request.getHeader("uuid");
		if(uuid == null) {
			uuid = UUID.randomUUID().toString();
		}
		MDC.put("uuid", uuid);
		MDC.put("uri", request.getRequestURI().toString());
	}
	
	private void postLogging(HttpServletResponse response,StopWatch stopWatch) {
		
		if(response != null && stopWatch != null) {			
			stopWatch.stop();
			logger.info("ResonseCode="+response.getStatus()+"|ResponseTime="+stopWatch.getLastTaskTimeMillis());
			String uui = MDC.get("uuid");			
		}		
	}
}