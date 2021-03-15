package com.lojavirtual.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lojavirtual.controller.utils.URL;
import com.lojavirtual.dto.ProdutoDTO;
import com.lojavirtual.entity.Produto;
import com.lojavirtual.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	private ProdutoService service;
	
	public ProdutoController(ProdutoService service) {
		this.service = service;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> findById(@PathVariable long id) {
		Produto produto = service.findById(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findAllPageable(
				@RequestParam(value = "nome", defaultValue = "") String nome,
				@RequestParam(value = "categorias", defaultValue = "") String categorias,
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") int linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC") String direction
			) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> produtosPaginados = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> produtosPaginadosDTO = produtosPaginados.map(produto -> new ProdutoDTO(produto));
		
		return ResponseEntity.ok().body(produtosPaginadosDTO);
	}
	
	@GetMapping(value = "/picture/{productPicture}")
	public ResponseEntity<Void> showProfilePicture(
				@PathVariable("productPicture") String productPicture,
				HttpServletResponse response
			) {
		service.showProductPicture(productPicture, response);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/picture/{id}")
	public ResponseEntity<Void> uploadProfilePicture(
			@RequestParam(name = "file") MultipartFile file,
			@PathVariable long id
		) {
		service.uploadProfilePicture(id, file);
		return ResponseEntity.noContent().build();
	}
	
}
