package com.delivery.tiago.api.exceptionhanddler;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ApiErros {
	
	private Integer status;
	private String dataHora;
	private String titulo;
	private List<Fields> field;
	
	@AllArgsConstructor
	@Getter
	public static class Fields {
		
		private String fieldName;
		private String fieldMsg;
		
	}

}
