package com.lojavirtual.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lojavirtual.entity.Cidade;
import com.lojavirtual.repository.CidadeRepository;

@Service
public class CidadeService {

	private CidadeRepository repository;
	
	public CidadeService(CidadeRepository repository) {
		this.repository = repository;
	}
	
	public List<Cidade> findCidadesByEstado(int estadoId) {
		return repository.findCidades(estadoId);
	}
	
}
