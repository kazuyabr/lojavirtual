package com.lojavirtual.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockMailService extends AbstractMailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOG.info("Simulando o envio de Email...");
		LOG.info(mailMessage.toString());
		LOG.info("Email enviado!");
	}

}
