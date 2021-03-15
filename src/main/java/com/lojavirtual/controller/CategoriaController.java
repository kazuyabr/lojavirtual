package com.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lojavirtual.dto.CategoriaDTO;
import com.lojavirtual.entity.Categoria;
import com.lojavirtual.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	private CategoriaService service;
	
	public CategoriaController(CategoriaService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = service.findAll();
		List<CategoriaDTO> categoriasDTO = categorias
				.stream()
				.map(categoria -> new CategoriaDTO(categoria))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
		Categoria categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping(value = "/picture/{categoriaPicture}")
	public ResponseEntity<Void> showProfilePicture(
				@PathVariable("categoriaPicture") String categoriaPicture,
				HttpServletResponse response
			) {
		service.showCategoriaPicture(categoriaPicture, response);
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria = service.insert(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaAtualizadaDTO, @PathVariable int id) {
		Categoria categoriaAtualizada = service.fromDTO(categoriaAtualizadaDTO);
		
		categoriaAtualizada.setId(id);
		service.update(categoriaAtualizada);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		service.delele(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findAllPageable(
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") int linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC") String direction
			) {
		Page<Categoria> categoriasPaginada = service.findAllPageable(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasPaginadaDTO = categoriasPaginada
				.map(categoria -> new CategoriaDTO(categoria));
		
		return ResponseEntity.ok().body(categoriasPaginadaDTO);
	}
	
}
