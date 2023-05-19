package com.demo.logging.config.filter;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.context.Context;



@Component
@Order(1)
public class LoggingWebFilter implements WebFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Override
	  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
	    ServerHttpRequest request = exchange.getRequest();
	    String uuid = getRequestId(request.getHeaders());
	    return chain
	        .filter(exchange)
	        .doOnEach(logOnEach(r -> logger.info("{} {}", request.getMethod(), request.getURI())))
	        .contextWrite(Context.of("CONTEXT_KEY", uuid));
	  }

	  private String getRequestId(HttpHeaders headers) {
		String uuid = headers.getFirst("uuid");
	    return uuid == null ? UUID.randomUUID().toString(): uuid;
	  }

	  public static <T> Consumer<Signal<T>> logOnEach(Consumer<T> logStatement) {
	    return signal -> {
	      String contextValue = signal.getContextView().get("CONTEXT_KEY");
	      try (MDC.MDCCloseable cMdc = MDC.putCloseable("uuid", contextValue)) {
	        logStatement.accept(signal.get());
	      }
	    };
	  }

	  public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> logStatement) {
	    return signal -> {
	      if (!signal.isOnNext()) return;
	      String contextValue = signal.getContextView().get("CONTEXT_KEY");
	      try (MDC.MDCCloseable cMdc = MDC.putCloseable("uuid", contextValue)) {
	        logStatement.accept(signal.get());
	      }
	    };
	  }
	
	
	public Mono<Void> filter1(ServerWebExchange exchange, WebFilterChain chain) {
		logger.info("Inside Logging Web Filter");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			return chain.filter(exchange).then(Mono.fromRunnable( () -> {
				configureMDC(exchange.getRequest());
				postLogging(exchange, stopWatch);
				}
			));
		} finally {
			MDC.clear();
		}
	}
	
	private void configureMDC(ServerHttpRequest request) {
		String uuid = request.getHeaders().getFirst("uuid");
		if(uuid == null) {
			uuid = UUID.randomUUID().toString();
		}
		MDC.put("uuid", uuid);
		MDC.put("uri", request.getURI().getPath());
	}
	
	private void postLogging(ServerWebExchange exchange,StopWatch stopWatch) {
		
		if(exchange != null && stopWatch != null) {			
			stopWatch.stop();
			logger.info("ResonseCode={}|ResponseTime={}",exchange.getResponse().getStatusCode().value(),stopWatch.getLastTaskTimeMillis());			
		}		
	}
}