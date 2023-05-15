package com.demo.logging.controller;

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

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/logging/demo")
	public String helloSleuth() {
		logger.info("Hello Sleuth");
		loggingDemoService.demo();
		loggingDemoService.asyncDemo();
		return "success";
	}

}
