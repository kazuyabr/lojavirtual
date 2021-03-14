package com.lojavirtual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.lojavirtual.entity.Cliente;
import com.lojavirtual.entity.Pedido;

public abstract class AbstractMailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(mailMessage);
	}
	
	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(pedido.getCliente().getEmail()); // Email que receberá a mensagem
		message.setFrom(sender); // Email que enviará a mensagem
		message.setSubject("Pedido confirmado! Código: " + pedido.getId());
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(pedido.toString());
		
		return message;
	}
	
	@Override
	public void sendNewPasswordByEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage message = prepareNewPasswordByEmail(cliente, novaSenha);
		sendEmail(message);
	}

	private SimpleMailMessage prepareNewPasswordByEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(cliente.getEmail());
		message.setFrom(sender);
		message.setSubject("Solicitação de nova senha");
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText("Nova senha: " + novaSenha);
		
		return message;
	}

}
