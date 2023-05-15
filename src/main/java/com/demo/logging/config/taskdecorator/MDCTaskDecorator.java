package com.demo.logging.config.taskdecorator;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

//For methods annotated with @Async annotation
public class MDCTaskDecorator implements TaskDecorator {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Runnable decorate(Runnable runnable) {
		logger.info("Inside MDC TaskDecorator");
		return new MDCDecoratedRunnable(runnable);
	}

	public static class MDCDecoratedRunnable implements Runnable {

		private final Runnable runnable;

		private final Map<String, String> contextMap;

		public MDCDecoratedRunnable(Runnable runnable) {
			super();
			this.runnable = runnable;
			this.contextMap = MDC.getCopyOfContextMap();
		}

		@Override
		public void run() {
			try {
				MDC.setContextMap(contextMap);
				runnable.run();
			} finally {
				MDC.clear();
			}
		}

	}
}
