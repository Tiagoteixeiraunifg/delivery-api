package com.delivery.tiago.api.controller.v1.user;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.tiago.api.assembler.UserAssembler;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.api.model.output.dto.response.Response;
import com.delivery.tiago.common.UtilApi;
import com.delivery.tiago.domain.exception.NegocioException;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.security.BcryptUtil;
import com.delivery.tiago.domain.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Api(value = "Cadastro de usuarios da API")
public class UserController {


	private UserAssembler userAssembler;
	private UserService service;
	private UserRepository repository;
	
	@ApiOperation(value = "Metodo para cadastro público do usuário.")
	@PostMapping(value = "/api/v1/user")
	@CrossOrigin(origins = "${front.baseurl}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<UserDTO>> postUser(@Valid @RequestBody UserDTO user, BindingResult result){

		Response<UserDTO> response = new Response<>();

		UtilApi utilApi = new UtilApi();
		if(utilApi.isValidEmailAddressRegex(user.getEmail())) {
			boolean emailUsed = repository.findByEmail(user.getEmail())
					.stream()
					.anyMatch(c -> !c.equals(user));
			if(emailUsed) {
				throw new NegocioException("Email em já está em uso em outro cadastro!");
			}
		}
				
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		

		
		if(user.getUserperfil() == null) {
			user.setUserperfil(UserPerfil.ADMIN.getValue());
		}
		
		user.setPassword(BcryptUtil.getHash(user.getPassword()));
		
		User userMdl = user.convertDTOToEntity();
		
	    userMdl = service.save(userMdl);
	    
	    response.setData(userMdl.convertEntityToDTO());
	    
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	
	}
	
	
	@ApiOperation(value = "Metodo para atualização do cadastro do usuário.")
	@PutMapping(value = "/api/v1/users")
	@CrossOrigin(origins = "${front.baseurl}")
	public ResponseEntity<Response<UserDTO>> putUser(@Valid @RequestBody UserDTO user, BindingResult result){
		
		User userLoggd = userAutheticated();
		
		Response<UserDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		String emailUser = userLoggd.getEmail();
		
		if (!emailUser.equals(user.getEmail())) {
			boolean emailUsed = repository.findByEmail(user.getEmail()).stream().anyMatch(c -> !c.equals(user));
			if (emailUsed) {
				response.addErrorMsgToResponse("Email em já está em uso em outro cadastro!");
				return ResponseEntity.badRequest().body(response);
			}
		}
		
		user.setPassword(BcryptUtil.getHash(user.getPassword()));
		
		User userMdl = user.convertDTOToEntity();
	    userMdl = service.update(userMdl);
	    
	    response.setData(userMdl.convertEntityToDTO());
	    
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	
	}
	
	
	
	@GetMapping(value = "/api/v1/users")
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Mostra Lista de Usuários Cadastrados para o Administrador do Sistema")
	public ResponseEntity<Response<List<UserDTO>>> getUsuarios() {

		Response<List<UserDTO>> response = new Response<>();

		User userLoggd = userAutheticated();

		if (userLoggd.getUserperfil().equals(UserPerfil.ADMIN)) {
			response.setData(userAssembler.toListUserDTO(repository.findAll()));
		} else {
			response.addErrorMsgToResponse("Somente administrador pode listar os usuários!");
			return ResponseEntity.badRequest().body(response);
		}
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

		return new ResponseEntity<>(response, headers, HttpStatus.OK);

	}
	
	
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Deleta um usuario cadastrado passando o ID.")
	@DeleteMapping(value = "/api/v1/users/{idUser}")
	public ResponseEntity<Void> delete(@PathVariable Integer idUser) {
		
		User userLoggd = userAutheticated();
		

		if (!repository.existsById(idUser)) {
			return ResponseEntity.notFound().build();
		}
		
		if(!userLoggd.getUserperfil().equals(UserPerfil.ADMIN)) {
			return ResponseEntity.badRequest().build();
		}
		service.deleteUser(idUser);
		return ResponseEntity.noContent().build();
	}
	
	
	private User userAutheticated() {
		User userLogged = new User();
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		String username;
		if (authenticated) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
			userLogged = userAssembler.toModel(repository.findByEmail(username));
		}
		return userLogged;
	}
}
