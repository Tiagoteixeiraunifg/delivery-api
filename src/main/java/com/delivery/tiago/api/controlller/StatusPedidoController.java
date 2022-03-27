package com.delivery.tiago.api.controlller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.tiago.api.assembler.StatusPedidoAssembler;
import com.delivery.tiago.api.model.StatusPedidoDTO;
import com.delivery.tiago.api.model.imput.StatusPedidoDTOimp;
import com.delivery.tiago.domain.model.Entrega;
import com.delivery.tiago.domain.model.StatusPedido;
import com.delivery.tiago.domain.service.BuscaEntregaService;
import com.delivery.tiago.domain.service.StatusPedidoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/statuspedido")
public class StatusPedidoController {

	private StatusPedidoService statusPedidoService;
	private StatusPedidoAssembler statusPedidoAssembler;
	private BuscaEntregaService buscaEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StatusPedidoDTO registrar(@PathVariable Long entregaId, @Valid @RequestBody StatusPedidoDTOimp statusImp ) {
		
		StatusPedido status = statusPedidoService
				.registrar(entregaId, statusImp.getDescricao());

		return statusPedidoAssembler.toModel(status);
	}
	
	@GetMapping
	public List<StatusPedidoDTO> listar(@PathVariable Long entregaId ) {
		Entrega entrega = buscaEntregaService.buscaEntrega(entregaId);
		return statusPedidoAssembler.toListModel(entrega.getStatusPedido());
	}
	
}
