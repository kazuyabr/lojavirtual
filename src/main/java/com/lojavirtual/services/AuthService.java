package com.lojavirtual.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lojavirtual.entity.Cliente;
import com.lojavirtual.repository.ClienteRepository;
import com.lojavirtual.services.exceptions.ObjectNotFoundException;


@Service
public class AuthService {

	private ClienteRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	@Autowired
	private BCryptPasswordEncoder enconder;
	
	
	public AuthService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public void sendNewPassword(String email) {
		Cliente cliente = repository.findByEmail(email);
		
		if (cliente == null)
			throw new ObjectNotFoundException("Email não encontrado!");
		
		String novaSenha = novaSenha();
		cliente.setSenha(enconder.encode(novaSenha));
		
		repository.save(cliente);
		emailService.sendNewPasswordByEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] caracteres = new char[10];
		
		for (int i = 0; i < 10; i++)
			caracteres[i] = randomChar();
		
		return new String(caracteres);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		
		if (opt == 0)
			return (char) (random.nextInt(10) + 48); // Gera um dígito
		else if (opt == 1)
			return (char) (random.nextInt(26) + 65);
		else
			return (char) (random.nextInt(26) + 97);
	}
	
}
