package com.delivery.tiago.api.assembler;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.delivery.tiago.api.model.output.dto.ClientesDTO;
import com.delivery.tiago.domain.model.Cliente;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClientesAssembler {
	
	private ModelMapper modelMapper;
	
	public ClientesDTO toModel(Cliente cliente) {
		
		return modelMapper.map(cliente, ClientesDTO.class);
		
	}
	
	public List<ClientesDTO> toListClientesTDO(List<Cliente> listClientes){
		return  listClientes.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
