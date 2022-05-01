package com.delivery.tiago.api.controller;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
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
import com.delivery.tiago.api.assembler.UserAssembler;
import com.delivery.tiago.api.model.output.dto.ClientesDTO;
import com.delivery.tiago.api.model.output.dto.response.Response;
import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.model.Cliente;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.repository.ClienteRepository;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.service.cliente.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@RestController	
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Cadastro de Clientes da API")
public class ClienteController {
	
	private ClienteService cliSevice;
	private ClienteRepository clienteRepository;
	private ClientesAssembler clienteAssembler;
	private UserRepository userRepository;
	private UserAssembler userAssembler;
	

	
	/**
	 * Author: Tiago Teixeira
	 * Metodo verifica o perfil do usuário, caso não 
	 * seja do tipo usuario é o admin com isso poderá 
	 * ver todos os registros
	 * @return
	 */	
	@ApiOperation(value = "Mostra Lista de Clientes Cadastrados pelo Usuario Logado no Sistema")
	@GetMapping
	public ResponseEntity<Response<List<ClientesDTO>>> findAllClientes(){
	
		Response<List<ClientesDTO>> response = new Response<>();
		
		User userLoggd = userAutheticated();
		
		if(userLoggd.getUserperfil().equals(UserPerfil.USUARIO)) {
			response.setData(clienteAssembler.toListClientesTDO(clienteRepository.findByUserId(userLoggd.getId())));
		}else {
			response.setData(clienteAssembler.toListClientesTDO(clienteRepository.findAll()));
		}
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "Busca Cliente Cadastrado pelo ID exemplo: dominio/clientes/ID")
	@GetMapping(value = "/{clienteId}")
	public ResponseEntity<Response<ClientesDTO>> findById(@PathVariable Integer clienteId){
		
		Response<ClientesDTO> response = new Response<>();
		
		User userLoggd = userAutheticated();
		
		
		if(userLoggd.getUserperfil().equals(UserPerfil.USUARIO)) {
			Optional<Cliente> cliOpt= cliSevice.findByIdAndUserId(clienteId, userLoggd.getId());
			if(cliOpt.isEmpty()) {
				throw new NegocioException("Cliente não encontrado");
			}else {
				response.setData(cliOpt.get().convertEntityToDTO());
			}
		}else {
			
			Optional<Cliente> cliOpt = cliSevice.findByIdOpt(clienteId);
			if(cliOpt.isEmpty()) {
				throw new NegocioException("Cliente não encontrado");
			}else {
				response.setData(cliOpt.get().convertEntityToDTO());
			}
		}
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	
	
	
	@ApiOperation(value = "Cadastra novo cliente.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<ClientesDTO>>  saveCliente(@Valid @RequestBody ClientesDTO dto, BindingResult result){
		
		Response<ClientesDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Cliente cliMdl = dto.convertDtoToEntity();
		Cliente cliSaved = clienteRepository.save(cliMdl);
		ClientesDTO cliDTOsaved = cliSaved.convertEntityToDTO();
		
		response.setData(cliDTOsaved);
	

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);		
	}
	
	
	
	
	@ApiOperation(value = "Atualiza um cliente cadastrado informando o ID do mesmo.")
	@PutMapping(value = "/{clienteId}")
	public ResponseEntity<ClientesDTO> updateCliente(@Valid @PathVariable Integer clienteId,
			@RequestBody Cliente cliModel){
		   if(!clienteRepository.existsById(clienteId)) {
			   return ResponseEntity.notFound().build();			   
		   } 

		   cliModel.setId(clienteId);
		   cliModel = cliSevice.saveCliente(cliModel);

		   return ResponseEntity.ok( clienteAssembler.toModel(cliModel));			
	}
	
	@ApiOperation(value = "Deleta um cliente cadastrado passando o ID.")
	@DeleteMapping(value = "/{clienteId}")
	public ResponseEntity<Void> delete(@PathVariable Integer clienteId){
		if(!clienteRepository.existsById(clienteId)) {

			   return ResponseEntity.notFound().build();			   
		}
		cliSevice.deleteCliente(clienteId);
		return ResponseEntity.noContent().build();
	}
	
	
	private User userAutheticated() {
		User userLogged = new User();
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		String username;
		if(authenticated) {
			username =  SecurityContextHolder.getContext().getAuthentication().getName();
			userLogged = userAssembler.toModel(userRepository.findByEmail(username));
		}
		return userLogged;
	}
	
}
