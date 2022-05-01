package com.delivery.tiago.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.tiago.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>	{
	
	List<Cliente> findByNome(String nome);
	
	List<Cliente> findByNomeAndUserId(String nome, Integer user_id);
	
	List<Cliente> findByUserId(Integer user_id);
	
	Optional<Cliente> findByIdAndUserId(Integer id, Integer user_id);
	
	Optional<Cliente> findById(Integer id);
	
	List<Cliente> findByNomeContaining(String nome);
	
	Optional<Cliente> findByEmail(String email);
	
}
