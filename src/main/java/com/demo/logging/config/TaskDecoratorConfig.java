package com.demo.logging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

import com.demo.logging.config.taskdecorator.MDCTaskDecorator;

@Configuration
public class TaskDecoratorConfig {
	@Bean
	TaskDecorator mdcCopyTaskDecorator() {
		return new MDCTaskDecorator();
	}
}
