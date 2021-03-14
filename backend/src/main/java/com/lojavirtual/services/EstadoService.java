package com.lojavirtual.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lojavirtual.entity.Estado;
import com.lojavirtual.repository.EstadoRepository;

@Service
public class EstadoService {

	private EstadoRepository repository;
	
	public EstadoService(EstadoRepository repository) {
		this.repository = repository;
	}
	
	public List<Estado> findAll() {
		return repository.findAllByOrderByNome();
	}
	
}
