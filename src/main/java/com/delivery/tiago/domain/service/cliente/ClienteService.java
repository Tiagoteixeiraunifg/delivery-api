package com.delivery.tiago.domain.service.cliente;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.model.Cliente;
import com.delivery.tiago.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	
	
	public Cliente findById(Integer id) {
		
		return  clienteRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cliente não cadastrado"));
		
	}
	
	public Optional<Cliente> findByIdAndUserId(Integer id, Integer userId) {
		
		return  clienteRepository.findByIdAndUserId(id, userId);
				
	}
	
	public Optional<Cliente> findByEmail(String email){
		
		return clienteRepository.findByEmail(email);
		
	}
	
	public Optional<Cliente> findByIdOpt(Integer id) {
		
		return  clienteRepository.findById(id);
				
	}
	
	
	@Transactional
	public Cliente saveCliente(Cliente cliModel) {

		return clienteRepository.save(cliModel);
		
	}
	
	
	public boolean emailEmUso(Cliente cliModel) {
		boolean emailEmUso = clienteRepository.findByEmail(cliModel.getEmail())
				.stream()
				.anyMatch(c -> !c.equals(cliModel));
		return emailEmUso;
	}

	
	@Transactional
	public void deleteCliente(Integer idCLiente) {
		
		clienteRepository.deleteById(idCLiente);
	}
	
}
