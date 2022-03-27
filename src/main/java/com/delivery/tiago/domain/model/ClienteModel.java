package com.delivery.tiago.domain.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.delivery.tiago.domain.ValidationsGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name ="cliente")
public class ClienteModel {
	
	@NotNull(groups = ValidationsGroup.clienteId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 255)
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max = 255)
	@Column(nullable = false)
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Column(nullable = false)
	private String telefone;
	
}
