package com.example.demo.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.MessagingService;

@Configuration
public class MyConfig {
	
	@Value("${url}")
	private String url;
	
	@Value("${port}")
	private int port;
	
	@Bean
	public MessagingService msgService() {
		return new MessagingService(url, port);
	}
}
