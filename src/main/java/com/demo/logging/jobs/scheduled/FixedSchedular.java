package com.demo.logging.jobs.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedSchedular {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	//@Scheduled(fixedDelay = 5 * 1000)
	void log() {
		logger.info("Inside Fixed Schedular");
	}


}
