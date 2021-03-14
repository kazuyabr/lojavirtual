package com.lojavirtual.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lojavirtual.controller.exceptions.FieldMessage;
import com.lojavirtual.dto.ClienteDTO;
import com.lojavirtual.entity.Cliente;
import com.lojavirtual.repository.ClienteRepository;

public class ClienteAtualizadoValidado implements 
	ConstraintValidator<ClienteAtualizado, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteAtualizado constraintAnnotation) {}
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean isValid(ClienteDTO cadastro, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> currentRequest = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		int uriId = Integer.parseInt(currentRequest.get("id"));
		
		List<FieldMessage> listaDeErros = new ArrayList<FieldMessage>();
		
		Cliente cliente = repository.findByEmail(cadastro.getEmail());
		
		if (cliente != null && !cliente.getId().equals(uriId))
			listaDeErros.add(new FieldMessage("email", "Email já cadastrado"));
		
		for (FieldMessage field : listaDeErros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(field.getMessage())
				.addPropertyNode(field.getFieldName())
				.addConstraintViolation();
		}
		
		return listaDeErros.isEmpty();
	}
	
}
