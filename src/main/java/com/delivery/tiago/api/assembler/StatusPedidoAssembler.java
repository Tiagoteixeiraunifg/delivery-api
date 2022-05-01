package com.delivery.tiago.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.delivery.tiago.api.model.output.dto.StatusPedidoDTO;
import com.delivery.tiago.domain.model.StatusPedido;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class StatusPedidoAssembler {
	
	private ModelMapper modelMapper;

	public StatusPedidoDTO toModel(StatusPedido status) {
		
		return modelMapper.map(status, StatusPedidoDTO.class);
		
	}
	
	public List<StatusPedidoDTO> toListModel(List<StatusPedido> list){
		return list.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
		
		
	}
}
