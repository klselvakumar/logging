package com.demo.logging.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.logging.async.LoggingDemoService;

@RestController
public class DemoController {

	@Autowired
	LoggingDemoService loggingDemoService;
	
	private static AtomicInteger atomicInteger = new AtomicInteger();

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/logging/demo")
	public String helloSleuth() throws InterruptedException {
		int i = atomicInteger.getAndIncrement();
		logger.info("Before Sleep {}", i);
		//Thread.sleep(5000);
		logger.info("After Sleep {}", i);
		return "success";
	}

}
