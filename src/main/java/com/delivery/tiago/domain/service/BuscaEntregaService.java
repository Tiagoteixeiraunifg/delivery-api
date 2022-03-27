package com.delivery.tiago.domain.service;

import org.springframework.stereotype.Service;

import com.delivery.tiago.domain.exception.EntidadeNaoEncontradaException;
import com.delivery.tiago.domain.model.Entrega;
import com.delivery.tiago.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service

public class BuscaEntregaService {
	
	private EntregaRepository entregaRepository; 

	public Entrega buscaEntrega(Long entregaId) {
		
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
		
	}
	
}
