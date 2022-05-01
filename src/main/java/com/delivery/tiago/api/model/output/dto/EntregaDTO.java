package com.delivery.tiago.api.model.output.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.delivery.tiago.domain.model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntregaDTO {

	private Long id;
	private Long IdCliente;
	private String nomeCliente;
	private DestinatarioDTO destinatario;
	private BigDecimal taxa;
	private StatusEntrega status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
	
	
}
