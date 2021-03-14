package com.lojavirtual.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractMailService {

	@Autowired
	private MailSender sender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOG.info("Enviando email...");
		sender.send(mailMessage);
		LOG.info("Email enviado!");
	}

}
