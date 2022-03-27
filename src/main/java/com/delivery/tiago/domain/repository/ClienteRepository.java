package com.delivery.tiago.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.tiago.domain.model.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long>	{
	
	List<ClienteModel> findByNome(String nome);
	List<ClienteModel> findByNomeContaining(String nome);
	Optional<ClienteModel> findByEmail(String email);
	
}
