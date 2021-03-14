package com.lojavirtual.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lojavirtual.dto.CategoriaDTO;
import com.lojavirtual.entity.Categoria;
import com.lojavirtual.repository.CategoriaRepository;
import com.lojavirtual.services.exceptions.DataIntegrityException;
import com.lojavirtual.services.exceptions.ObjectNotFoundException;
import com.lojavirtual.services.utils.UploadService;

@Service
public class CategoriaService {

	private CategoriaRepository repository;
	
	@Autowired
	private UploadService uploadService;
	
	public CategoriaService(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		return categoria.orElseThrow(
				() -> new ObjectNotFoundException("Categoria não encontrada! Id informado: " + id)
			);
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		Categoria categoriaAtualizada = findById(categoria.getId());
		
		categoriaAtualizada.setNome(categoria.getNome());
		
		return repository.save(categoriaAtualizada);
	}
	
	public void delele(int id) {
		findById(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir esta categoria, pois existem produtos relacionados a ela");
		}
	}
	
	public Page<Categoria> findAllPageable(int page, int linesPerPage, String orderBy, String direction) {
		PageRequest categoriasPaginada = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(categoriasPaginada);
	}

	public Categoria fromDTO(@Valid CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

	public void showCategoriaPicture(String categoriaPicture, HttpServletResponse response) {
		uploadService.showData("/categorias_img/" + categoriaPicture, response);
	}
	
}
