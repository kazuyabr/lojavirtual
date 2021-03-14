package com.lojavirtual.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojavirtual.dto.CidadeDTO;
import com.lojavirtual.dto.EstadoDTO;
import com.lojavirtual.entity.Cidade;
import com.lojavirtual.entity.Estado;
import com.lojavirtual.services.CidadeService;
import com.lojavirtual.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	private EstadoService service;
	private CidadeService cidadeService;
	
	public EstadoController(EstadoService service, CidadeService cidadeService) {
		this.service = service;
		this.cidadeService = cidadeService;
	}
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = service.findAll();
		List<EstadoDTO> estadosDTO = estados.stream()
				.map(estado -> new EstadoDTO(estado))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@GetMapping(value = "/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable int estadoId) {
		List<Cidade> cidades = cidadeService.findCidadesByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream()
				.map(cidade -> new CidadeDTO(cidade))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDTO);
	}
	
}
