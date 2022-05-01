package com.delivery.tiago.api.model.imput.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusPedidoDTOimp {

	@NotBlank
	private String descricao;
	
}
