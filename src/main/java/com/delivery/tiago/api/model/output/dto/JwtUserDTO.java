package com.delivery.tiago.api.model.output.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDTO {
	
	@NotNull(message = "Enter an email")
	@NotEmpty(message = "Enter an email")
	private String email;
	
	@NotNull(message = "Enter a password")
	@NotEmpty(message = "Enter a password")
	private String password;
	
}
