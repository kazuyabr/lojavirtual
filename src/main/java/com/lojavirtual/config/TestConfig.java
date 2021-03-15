package com.lojavirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lojavirtual.services.EmailService;
import com.lojavirtual.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public EmailService emailService() {
		return new MockMailService();
	}
	
}
