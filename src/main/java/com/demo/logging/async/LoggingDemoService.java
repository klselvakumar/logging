package com.demo.logging.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LoggingDemoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Async
	public void asyncDemo() {
		logger.info("Inside asyncDemo");
	}
	
	public void demo() {
		logger.info("Inside demo");
	}

}
