package com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lojavirtual.entity.Categoria;
import com.lojavirtual.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT prod FROM Produto prod INNER JOIN prod.categorias cat WHERE prod.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param(value = "nome") String nome, @Param(value = "categorias") List<Categoria> categorias, Pageable pageRequest);
	
}
