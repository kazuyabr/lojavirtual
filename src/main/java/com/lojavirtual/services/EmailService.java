package com.lojavirtual.services;

import org.springframework.mail.SimpleMailMessage;

import com.lojavirtual.entity.Cliente;
import com.lojavirtual.entity.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage mailMessage);
	
	void sendNewPasswordByEmail(Cliente cliente, String novaSenha);
	
}
