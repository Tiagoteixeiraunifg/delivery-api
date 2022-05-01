package com.delivery.tiago.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.modelmapper.ModelMapper;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.domain.ValidationsGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name ="Usuario")
public class User {

	@EqualsAndHashCode.Include
	@Id
	@NotNull(groups = ValidationsGroup.userId.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100)
	@NotBlank
	private String nome;
	
	@NotBlank
	@Column(length = 100)
	private String sobrenome;
	
	@Email
	@Column(length = 100)
	private String email;
	
	@NotBlank
	@EqualsAndHashCode.Include
	@Column(length = 250)
	private String password;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private UserPerfil userperfil;
	
	public UserDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserDTO.class);
	}
	
	public boolean isAdmin() {
		return UserPerfil.ADMIN.toString().equals(this.userperfil.toString());
	}
}
