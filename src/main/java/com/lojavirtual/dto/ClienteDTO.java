package com.lojavirtual.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lojavirtual.entity.Cliente;
import com.lojavirtual.services.validation.ClienteAtualizado;

@ClienteAtualizado
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento do nome é obrigatório")
	@Length(min = 5, max = 80, message = "O nome deve ter entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento do email é obrigatório")
	@Email(message = "Este email não é valido")
	private String email;
	
	public ClienteDTO() {}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
