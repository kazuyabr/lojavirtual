package com.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lojavirtual.dto.ClienteCadastroDTO;
import com.lojavirtual.dto.ClienteDTO;
import com.lojavirtual.entity.Cliente;
import com.lojavirtual.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	private ClienteService service;
	
	public ClienteController(ClienteService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = service.findAll();
		List<ClienteDTO> clientesDTO = clientes.stream()
				.map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> findClientById(@PathVariable long id) {
		Cliente cliente = service.findById(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping(value = "/email")
	public ResponseEntity<Cliente> findByEmail(@RequestParam("value") String email) {
		Cliente cliente = service.findClienteByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping(value = "/picture/{clientPicture}")
	public ResponseEntity<Void> showProfilePicture(
				@PathVariable("clientPicture") String clientPicture,
				HttpServletResponse response
			) {
		service.showProfilePicture(clientPicture, response);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteCadastroDTO clienteDTO) {
		Cliente cliente = service.fromDTO(clienteDTO);
		cliente = service.insert(cliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value = "/picture")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable long id) {
		Cliente cliente = service.fromDTO(clienteDTO);
		
		cliente.setId(id);
		cliente = service.update(cliente);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findAllPageable(
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") int linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC") String direction
			) {
		Page<Cliente> clientePaginados = service.findAllPageable(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> clientesPaginadosDTO = clientePaginados.map(cliente -> new ClienteDTO(cliente));
		
		return ResponseEntity.ok().body(clientesPaginadosDTO);
	}

}
