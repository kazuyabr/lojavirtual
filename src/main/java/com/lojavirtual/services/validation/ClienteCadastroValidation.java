package com.lojavirtual.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lojavirtual.controller.exceptions.FieldMessage;
import com.lojavirtual.dto.ClienteCadastroDTO;
import com.lojavirtual.entity.Cliente;
import com.lojavirtual.enums.TipoCliente;
import com.lojavirtual.repository.ClienteRepository;
import com.lojavirtual.services.validation.utils.BR;

public class ClienteCadastroValidation implements 
	ConstraintValidator<ClienteCadastro, ClienteCadastroDTO> {
	
	@Autowired
	private ClienteRepository repository;

	@Override
	public void initialize(ClienteCadastro constraintAnnotation) {}
	
	@Override
	public boolean isValid(ClienteCadastroDTO cadastro, ConstraintValidatorContext context) {
		List<FieldMessage> listaDeErros = new ArrayList<FieldMessage>();
		
		if (cadastro.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(cadastro.getCpfOuCnpj())) {
			listaDeErros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if (cadastro.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(cadastro.getCpfOuCnpj())) {
			listaDeErros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cliente = repository.findByEmail(cadastro.getEmail());
		
		if (cliente != null) 
			listaDeErros.add(new FieldMessage("email", "Este email já está cadastrado"));
		
		for (FieldMessage field : listaDeErros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(field.getMessage())
				.addPropertyNode(field.getFieldName())
				.addConstraintViolation();
		}
		
		return listaDeErros.isEmpty();
	}

}
