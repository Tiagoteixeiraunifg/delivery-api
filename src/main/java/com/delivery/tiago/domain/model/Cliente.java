package com.delivery.tiago.domain.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.modelmapper.ModelMapper;

import com.delivery.tiago.api.model.output.dto.ClientesDTO;
import com.delivery.tiago.domain.ValidationsGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name ="cliente")
public class Cliente {
	
	@NotNull(groups = ValidationsGroup.clienteId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@ManyToOne
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationsGroup.userId.class)
	private User user;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(nullable = false)
	private String sobrenome;
	
	@NotBlank
	@Size(max = 16)
	@Column(nullable = false)
	private String cpf;
	
	@NotBlank
	@Size(max = 20)
	@Column(nullable = false)
	private String telefone;
	
	@NotBlank
	@Email
	@Size(max = 255)
	@Column(nullable = false)
	private String email;
	
	
	@NotBlank
	@Size(max = 150)
	@Column(nullable = false)
	private String end_rua;
	
	
	@NotBlank
	@Size(max = 150)
	@Column(nullable = true)
	private String end_complemento;
	
	
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false)
	private String end_cidade;
	
	
	@NotBlank
	@Size(max = 20)
	@Column(nullable = false)
	private String end_numero;
	
	
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false)
	private String end_estado;
	
	
	@NotBlank
	@Size(max = 10)
	@Column(nullable = false)
	private String end_cep;
	
	public ClientesDTO convertEntityToDTO() {
		return new ModelMapper().map(this, ClientesDTO.class);
	}
}