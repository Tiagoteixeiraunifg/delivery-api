package com.delivery.tiago.domain.service.entrega;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.delivery.tiago.domain.model.Entrega;
import com.delivery.tiago.domain.model.StatusPedido;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StatusPedidoService {
	
	private BuscaEntregaService buscaEntregaService;
	
	@Transactional
	public StatusPedido registrar(Long idEntrega, String descricao) {
		Entrega entrega = buscaEntregaService.buscaEntrega(idEntrega);
		return entrega.adicionarStatusPedido(descricao);
			
	}
	
}
