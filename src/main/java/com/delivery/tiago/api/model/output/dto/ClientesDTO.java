package com.delivery.tiago.api.model.output.dto;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;
import com.delivery.tiago.domain.ValidationsGroup;
import com.delivery.tiago.domain.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientesDTO {
	

	private Integer id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationsGroup.userId.class)
	private UserDTO user;
	
	@Length(min=5, max=100, message="O campo nome deve ter no minimo 10 e no máximo 200 caracteres")
	@NotNull(message = "O campo Nome não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String nome;
	
	@NotNull(message = "O campo Sobreome não pode ser NULL")
	@NotBlank(message = "O campo Sobreome não pode está em BRANCO")
	private String sobrenome;
	
	@NotNull(message = "O campo CPF não pode ser NULL")
	@NotBlank(message = "O campo CPF não pode está em BRANCO")
	@CPF(message = "O Campo CPF está invalido")
	private String cpf;
	
	@NotNull(message = "O campo Telefone não pode ser NULL")
	@NotBlank(message = "O campo Telefone não pode está em BRANCO")
	private String telefone;
	
	@NotNull(message = "O campo E-Mail não pode ser NULL")
	@Email(message = "Email inválido tente novamente")
	@NotBlank(message = "O campo E-Mail não não pode está em BRANCO")
	private String email;
	
	@NotNull(message = "O campo Rua não pode ser NULL")
	@NotBlank(message = "O campo Rua não pode está em BRANCO")
	private String end_rua;
	
	@NotNull(message = "O campo Complemento não pode ser NULL")
	@NotBlank(message = "O campo Complemento não pode está em BRANCO")
	private String end_complemento;
	
	@NotNull(message = "O campo Cidade não pode ser NULL")
	@NotBlank(message = "O campo Cidade não pode está em BRANCO")
	private String end_cidade;
	
	@NotNull(message = "O campo Número não pode ser NULL")
	@NotBlank(message = "O campo Número não pode está em BRANCO")
	private String end_numero;
	
	@NotNull(message = "O campo Estado não pode ser NULL")
	@NotBlank(message = "O campo Estado não pode está em BRANCO")
	private String end_estado;
	
	@NotNull(message = "O campo CEP não pode ser NULL")
	@NotBlank(message = "O campo CEP não pode está em BRANCO")
	private String end_cep;

	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime datacriacao;
	
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime dataatualizacao;
	
	public Cliente convertDtoToEntity() {
		return new ModelMapper().map(this, Cliente.class);
	}
}
