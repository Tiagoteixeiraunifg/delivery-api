package com.delivery.tiago.api.assembler;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.delivery.tiago.api.model.ClientesDTO;
import com.delivery.tiago.domain.model.ClienteModel;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClientesAssembler {
	
	private ModelMapper modelMapper;
	
	public ClientesDTO toModel(ClienteModel cliente) {
		
		return modelMapper.map(cliente, ClientesDTO.class);
		
	}
	
	public List<ClientesDTO> toListClientesTDO(List<ClienteModel> listClientes){
		return  listClientes.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
