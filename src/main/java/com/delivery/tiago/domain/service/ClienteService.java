package com.delivery.tiago.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.model.ClienteModel;
import com.delivery.tiago.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	
	public ClienteModel findById(Long id) {
		
		return  clienteRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cliente não cadastrado"));
		
	}
	
	
	@Transactional
	public ClienteModel saveCliente(ClienteModel cliModel) {
		boolean emailEmUso = clienteRepository.findByEmail(cliModel.getEmail())
				.stream()
				.anyMatch(c -> !c.equals(cliModel));
		
		if(emailEmUso) {
			throw new NegocioException("Email em uso em outro cadastro!");
		}
		return clienteRepository.save(cliModel);
		
	}

	@Transactional
	public void deleteCliente(Long idCLiente) {
		
		clienteRepository.deleteById(idCLiente);
	}
	
	public ClienteModel updateCliente(Long IdCliente, ClienteModel cliModel) {
		
		return cliModel;
		
	}
}
