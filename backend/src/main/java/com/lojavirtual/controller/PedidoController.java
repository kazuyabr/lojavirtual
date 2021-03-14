package com.lojavirtual.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lojavirtual.entity.Pedido;
import com.lojavirtual.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Page<Pedido>> findPedidoByCliente(
				@RequestParam(value = "page", defaultValue = "0") int page, 
				@RequestParam(value = "linesPerPage", defaultValue = "24") int linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
				@RequestParam(value = "direction", defaultValue = "DESC") String direction
			) {
		Page<Pedido> pedidosDoCliente = service.findPedidoByCliente(page, linesPerPage, direction, orderBy);
		return ResponseEntity.ok().body(pedidosDoCliente);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable long id) {
		Pedido pedido = service.findById(id);
		return ResponseEntity.ok().body(pedido);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Pedido pedido) {
		pedido = service.insert(pedido);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}
