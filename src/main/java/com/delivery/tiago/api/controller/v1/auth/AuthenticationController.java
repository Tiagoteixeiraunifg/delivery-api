package com.delivery.tiago.api.controller.v1.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.tiago.api.assembler.UserAssembler;
import com.delivery.tiago.api.model.output.dto.JwtUserDTO;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.api.model.output.dto.response.Response;
import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.security.JwtTokenUtil;
import com.delivery.tiago.domain.service.security.impl.JwtUserDetailsServiceImpl;
import com.delivery.tiago.domain.service.security.impl.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1/user/auth")
@CrossOrigin(origins = "${front.baseurl}")
@Api(value = "Autenticação de usuarios da API - Tipo de Retorno JSON Usuário e Token no Reader")
public class AuthenticationController {
	
	@Autowired(required=true)
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserAssembler assembler;

	@ApiOperation(value = "Faz o login do Usuario no Sistema")
	@PostMapping
	public ResponseEntity<Response<UserDTO>> generateTokenJwt(@Valid @RequestBody JwtUserDTO dto, 
															 BindingResult result) throws AuthenticationException {
		
		Response<UserDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
		
		Authentication authentication = authenticationManager.authenticate
				(usernamePasswordAuthenticationToken);
		
		String token1 = tokenService.generateToken(authentication);
		
		if(authentication.isAuthenticated()) {
			response.setData(assembler.toModelOptional(repository.findByEmail(dto.getEmail())));
		}else {
			throw new NegocioException("Erro ao autenticar usuário!");
		}
		UserDTO userDTO = assembler.toModelOptional(repository.findByEmail(dto.getEmail()));
		userDTO.setToken(token1);
		response.setData(userDTO);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", token1);
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

}
