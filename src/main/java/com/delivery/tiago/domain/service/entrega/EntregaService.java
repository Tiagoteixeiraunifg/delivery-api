package com.delivery.tiago.domain.service.entrega;


import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.delivery.tiago.domain.model.Cliente;
import com.delivery.tiago.domain.model.Entrega;
import com.delivery.tiago.domain.model.StatusEntrega;
import com.delivery.tiago.domain.repository.EntregaRepository;
import com.delivery.tiago.domain.service.cliente.ClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EntregaService {
	
	private EntregaRepository entregaRepository;
	private ClienteService clienteService;
	private BuscaEntregaService buscaEntregaService;
	
	@Transactional
	public Entrega solicitar(Entrega entrega) {
		Cliente cliModel = clienteService.findById(entrega.getCliente().getId());			
		entrega.setCliente(cliModel);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		
		return entregaRepository.save(entrega);
	}
	
	@Transactional
	public void finalizar(Long entregaId) {
		Entrega entrega = buscaEntregaService.buscaEntrega(entregaId);
		entrega.finalizar();
		entregaRepository.save(entrega);
	}
}
