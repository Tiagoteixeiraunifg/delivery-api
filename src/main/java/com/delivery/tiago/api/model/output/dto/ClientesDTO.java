package com.delivery.tiago.api.model.output.dto;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import org.modelmapper.ModelMapper;
import com.delivery.tiago.domain.ValidationsGroup;
import com.delivery.tiago.domain.model.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientesDTO {
	
	private Integer id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationsGroup.userId.class)
	private UserDTO user;
	
	@NotNull(message = "O campo Nome não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String nome;
	
	@NotNull(message = "O campo Sobreome não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode ser NULL")
	private String sobrenome;
	
	@NotNull(message = "O campo CPF não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode ser NULL")
	private String cpf;
	
	@NotNull(message = "O campo Telefone não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode ser NULL")
	private String telefone;
	
	@NotNull(message = "O campo E-Mail não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode ser NULL")
	private String email;
	
	@NotNull(message = "O campo Rua não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_rua;
	
	@NotNull(message = "O campo Complemento não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_complemento;
	
	@NotNull(message = "O campo Cidade não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_cidade;
	
	@NotNull(message = "O campo Número não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_numero;
	
	@NotNull(message = "O campo Estado não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_estado;
	
	@NotNull(message = "O campo CEP não pode ser NULL")
	@NotBlank(message = "O campo Nome não pode está em BRANCO")
	private String end_cep;

	public Cliente convertDtoToEntity() {
		return new ModelMapper().map(this, Cliente.class);
	}
}
