package com.delivery.tiago.api.model.output.dto;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;

import com.delivery.tiago.domain.ValidationsGroup;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	@NotNull(groups = ValidationsGroup.userId.class)
	private Long id;
	
	@NotNull(message = "O campo Nome não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode ser em branco")
	private String nome;
	
	@NotNull(message = "O campo Sobrenome não pode ser NULL")
	@NotBlank(message = "O campo Sobrenome não pode ser em branco")
	private String sobrenome;
	
	@NotNull(message = "O campo E-mail não pode ser NULL")
	@NotBlank(message = "O campo E-mail não pode ser em branco")
	@Email(message = "O email está inválido, tente novamente")
	private String email;
	
	@NotNull(message = "O campo Password não pode ser NULL")
	@NotBlank(message = "O campo E-Password não pode ser em branco")
	private String password;
	
	@JsonProperty(access = Access.AUTO)
	@Enumerated(EnumType.STRING)
	private UserPerfil userperfil;
		
	private String token;
	
	public User convertDTOToEntity() {
		return new ModelMapper().map(this, User.class);
	}
	
}
