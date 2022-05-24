package com.delivery.tiago.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.delivery.tiago.api.model.output.dto.ClientesDTO;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.domain.model.Cliente;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.service.cliente.ClienteService;
import com.delivery.tiago.domain.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClientControllerTest {

	static final String URL = "/api/v1/clientes";
	static final int ID_CLI = 1;
	static final String NOME = "TIAGO";
	static final String SOBRENOME = "TEIXEIRA";
	static final String CPF = "03086170530";
	static final String TELEFONE = "77991668855";
	static final String EMAIL = "TIAGO@TIAGO.COM";
	static final String END_RUA = "RUA DE TESTE PARA O TESTE";
	static final String END_COMPLEMENTO = "COMPLEMENTO DE TESTE";
	static final String END_CIDADE = "GUANAMBI";
	static final String END_NUMERO = "123456";
	static final String END_ESTADO = "BAHIA";
	static final String END_CEP = "46430000";
	

	HttpHeaders headers;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ClienteService clientService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
		
	}
	
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(clientService.saveCliente(Mockito.any(Cliente.class))).willReturn(getMockCliente());
		
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID_CLI, getMockUser().convertEntityToDTO(), NOME, SOBRENOME, CPF,
				TELEFONE, EMAIL, END_RUA, END_COMPLEMENTO,END_CIDADE,END_NUMERO,END_ESTADO, END_CEP ))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.nome").value(NOME))
		.andExpect(jsonPath("$.data.sobrenome").value(SOBRENOME))
		.andExpect(jsonPath("$.data.cpf").value(CPF))
		.andExpect(jsonPath("$.data.telefone").value(TELEFONE))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.end_rua").value(END_RUA))
		.andExpect(jsonPath("$.data.end_complemento").value(END_COMPLEMENTO))
		.andExpect(jsonPath("$.data.end_cidade").value(END_CIDADE))
		.andExpect(jsonPath("$.data.end_numero").value(END_NUMERO))
		.andExpect(jsonPath("$.data.end_estado").value(END_ESTADO))
		.andExpect(jsonPath("$.data.end_cep").value(END_CEP));
		
	}
	
	
	
	
	private User getMockUser() {
		return new User(2, "Test User", "Test Sobrenome", "123", EMAIL, UserPerfil.ADMIN );
	}
	
	
	private Cliente getMockCliente() {
		return new Cliente(ID_CLI,getMockUser(), NOME, SOBRENOME, CPF,
							TELEFONE, EMAIL, END_RUA, END_COMPLEMENTO,END_CIDADE,
							END_NUMERO,END_ESTADO, END_CEP  );
	}
	
	
	private String getJsonPayload(int id, UserDTO usrDTO,String name, String lastName, 
								String cpf, String telefone, String email, String endRua,
								String endComplemento, String endCidade, String endNumero, 
								String endEstado, String endCep) throws JsonProcessingException {
		
		ClientesDTO dto = new ClientesDTO(id, usrDTO, name, lastName, cpf, telefone, email, 
											endRua, endComplemento,	endCidade, endNumero,
											endEstado, endCep);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	
	}
}
