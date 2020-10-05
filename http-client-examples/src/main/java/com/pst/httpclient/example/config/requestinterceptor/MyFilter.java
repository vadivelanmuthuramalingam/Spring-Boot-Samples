package com.pst.httpclient.example.config.requestinterceptor;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class MyFilter implements Filter {
	// @Autowired
	// SillyLog sillyLog;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse myResponse = (HttpServletResponse) response;

		org.slf4j.MDC.put("correlationId", UUID.randomUUID().toString());
		log.info("Enter in Filter...");

		log.info("Filter: URL" + " called: " + httpRequest.getRequestURL().toString());

		try {
			org.slf4j.MDC.put("correlationId", UUID.randomUUID().toString());
			chain.doFilter(request, response);

		} finally {
			log.info("Enter filter finally");
			//MDC.remove("correlationId");
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Enter Filter INIT method");

	}

	@Override
	public void destroy() {
		//log.info("Enter Filter DESTROY method");
		MDC.remove("correlationId");

	}
}