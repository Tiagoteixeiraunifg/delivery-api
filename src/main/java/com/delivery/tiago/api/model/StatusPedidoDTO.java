package com.delivery.tiago.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusPedidoDTO {

	private Long id;
	private String descricao;
	private OffsetDateTime dataRegistro;
	
}
