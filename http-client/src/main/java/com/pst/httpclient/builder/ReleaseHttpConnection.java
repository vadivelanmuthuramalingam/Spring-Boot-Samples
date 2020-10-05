package com.pst.httpclient.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReleaseHttpConnection {

	List<RestTemplate> lstOfHttpConnection = new ArrayList<RestTemplate>();

	public void setRestTemplates(RestTemplate restTemplate) {
		lstOfHttpConnection.add(restTemplate);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("httpConnectionMonitor");
		scheduler.setPoolSize(5);
		return scheduler;
	}

	@Scheduled(fixedDelay = 20000)
	public void closeHttpConnection() {
		for (RestTemplate restTemplate : lstOfHttpConnection) {

			ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
			if (factory instanceof PoolingHttpClientConnectionManager) {
				PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = (PoolingHttpClientConnectionManager) factory;
				if (poolingHttpClientConnectionManager != null) {
					poolingHttpClientConnectionManager.closeExpiredConnections();
					poolingHttpClientConnectionManager.closeIdleConnections(30000, TimeUnit.MILLISECONDS);

					log.info("Idle connection monitor: Closing expired and idle connections");
				}

			}
		}
	}

}
