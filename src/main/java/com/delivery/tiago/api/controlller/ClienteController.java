package com.delivery.tiago.api.controlller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.tiago.api.assembler.ClientesAssembler;
import com.delivery.tiago.api.model.ClientesDTO;
import com.delivery.tiago.domain.model.ClienteModel;
import com.delivery.tiago.domain.repository.ClienteRepository;
import com.delivery.tiago.domain.service.ClienteService;

import lombok.AllArgsConstructor;



@AllArgsConstructor
@RestController	
@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService cliSevice;
	private ClienteRepository clienteRepository;
	private ClientesAssembler clienteAssembler;
		
	@GetMapping
	public List<ClientesDTO> findAll(){
		return clienteAssembler.toListClientesTDO(clienteRepository.findAll());
	}
	
	
	@GetMapping(value = "/{clienteId}")
	public ResponseEntity<ClientesDTO> findById(@PathVariable Long clienteId){
		return clienteRepository.findById(clienteId)
				.map(cliente -> {
					ClientesDTO clienteDTO = clienteAssembler.toModel(cliente);
					return ResponseEntity.ok(clienteDTO);
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientesDTO saveCliente(@Valid @RequestBody ClienteModel cliModel){
		
		return  clienteAssembler.toModel(cliSevice.saveCliente(cliModel));
					
	}
	
	
	@PutMapping(value = "/{clienteId}")
	public ResponseEntity<ClientesDTO> updateCliente(@Valid @PathVariable Long clienteId,
			@RequestBody ClienteModel cliModel){
		   //verificar se não existe o cliente
		   if(!clienteRepository.existsById(clienteId)) {
			   //dispara erro caso o id do cliente não existir
			   return ResponseEntity.notFound().build();			   
		   } 
		   //adiacionando o ID no objeto para forçar o JPA a atualizar
		   cliModel.setId(clienteId);
		   cliModel = cliSevice.saveCliente(cliModel);
		   // retorna um body JSSON com o objeto atualizado
		   return ResponseEntity.ok( clienteAssembler.toModel(cliModel));
					
	}
	
	@DeleteMapping(value = "/{clienteId}")
	public ResponseEntity<Void> delete(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			   //dispara erro caso o id do cliente não existir
			   return ResponseEntity.notFound().build();			   
		}
		cliSevice.deleteCliente(clienteId);
		return ResponseEntity.noContent().build();
	}
	
}