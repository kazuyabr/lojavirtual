package com.lojavirtual.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lojavirtual.entity.Categoria;
import com.lojavirtual.entity.Produto;
import com.lojavirtual.enums.Perfil;
import com.lojavirtual.repository.CategoriaRepository;
import com.lojavirtual.repository.ProdutoRepository;
import com.lojavirtual.security.UserSS;
import com.lojavirtual.services.exceptions.AuthorizationException;
import com.lojavirtual.services.exceptions.ObjectNotFoundException;
import com.lojavirtual.services.utils.UploadService;

@Service
public class ProdutoService {

	private ProdutoRepository repository;
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private UploadService uploadService;
	
	@Value("${img.prefix.product.profile}")
	private String prefix;
	
	public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository, 
			ClienteService clienteService) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}
	
	public Produto findById(long id) {
		Optional<Produto> produto = repository.findById(id);
		return produto.orElseThrow(
				() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id)
			);
	}
	
	public Page<Produto> search(String nome, List<Integer> categoriasId, int page, int linesPerPage, String orderBy, String direction) {
		PageRequest produtosPaginados = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(categoriasId);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, produtosPaginados);
	}

	public void showProductPicture(String productPicture, HttpServletResponse response) {
		uploadService.showData("/produtos_img/" + productPicture, response);
	}

	public void uploadProfilePicture(long id, MultipartFile file) {
		UserSS user = UserService.authenticaded();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !user.getId().equals(id)) 
			throw new AuthorizationException("Acesso negado!");
		
		Produto produto = findById(id);
		produto.setImgPath(uploadService.uploadPicture(file, id, prefix, "/produtos_img/"));
		repository.save(produto);
	}
	
}
