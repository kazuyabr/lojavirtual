package com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lojavirtual.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT cids FROM Cidade cids WHERE cids.estado.id = :estadoId ORDER BY cids.nome")
	public List<Cidade> findCidades(@Param("estadoId") int estadoId);
	
}
