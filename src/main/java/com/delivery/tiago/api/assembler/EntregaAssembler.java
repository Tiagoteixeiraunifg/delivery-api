package com.delivery.tiago.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.delivery.tiago.api.model.EntregaDTO;
import com.delivery.tiago.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaAssembler {
	
	private ModelMapper modelMapper;
	
	public EntregaDTO toModel(Entrega emtrega) {
		
		return modelMapper.map(emtrega, EntregaDTO.class);
		
	}
	
	public List<EntregaDTO> toListEntrgaTDO(List<Entrega> listEntrega){
		return  listEntrega.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
