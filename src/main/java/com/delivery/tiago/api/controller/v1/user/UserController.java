package com.delivery.tiago.api.controller.v1.user;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.api.model.output.dto.response.Response;
import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.security.BcryptUtil;
import com.delivery.tiago.domain.service.user.UserService;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
@Api(value = "Cadastro de usuarios da API")
public class UserController {
	

	private UserService service;
	private UserRepository repository;
	
	@PostMapping
	@CrossOrigin(origins = "${front.baseurl}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<UserDTO>> postUser(@Valid @RequestBody UserDTO user, BindingResult result){

		Response<UserDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		boolean emailUsed = repository.findByEmail(user.getEmail())
				.stream()
				.anyMatch(c -> !c.equals(user));
		if(emailUsed) {
			throw new NegocioException("Email em já está em uso em outro cadastro!");
		}
		
		if(user.getUserperfil() == null) {
			user.setUserperfil(UserPerfil.ADMIN);
		}
		
		user.setPassword(BcryptUtil.getHash(user.getPassword()));
		
		User userMdl = user.convertDTOToEntity();
		
	    userMdl = service.save(userMdl);
	    
	    response.setData(userMdl.convertEntityToDTO());
	    
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	
	}
	
	
	
}
