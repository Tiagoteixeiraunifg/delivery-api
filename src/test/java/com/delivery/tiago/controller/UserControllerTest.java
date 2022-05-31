package com.delivery.tiago.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;


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
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
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
public class UserControllerTest {

	static final String URL = "/api/v1/user";
	static final String URL2 = "/api/v1/users";
	static final Long ID = 1L;
	static final String NAME = "Test User";
	static final String LAST_NAME = "Test User Last";
	static final String PASSWORD = "123";
	static final String EMAIL = "email@test.com";
	static final String TOKEN = "test";	
	static final LocalDateTime DATACRIACAO = LocalDateTime.now();
	static final LocalDateTime DATAATUALIZACAO = LocalDateTime.now();
	
	HttpHeaders headers;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
		
	}
	
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, LAST_NAME, EMAIL, 
											PASSWORD, UserPerfil.ADMIN, TOKEN, DATACRIACAO, DATAATUALIZACAO))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.nome").value(NAME))
		.andExpect(jsonPath("$.data.sobrenome").value(LAST_NAME))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.password").value(PASSWORD))
		.andExpect(jsonPath("$.data.userperfil").value(UserPerfil.ADMIN.getValue()));
		
	}
	
	
	private User getMockUser() {
		return new User(1, NAME, LAST_NAME, EMAIL, PASSWORD, UserPerfil.ADMIN, DATACRIACAO, DATAATUALIZACAO );
	}
	
	
	private String getJsonPayload(Long id, String name, String lastName, String email, String password,
			UserPerfil userPerfil, String token, LocalDateTime datacriacao, LocalDateTime dataatualizacao ) throws JsonProcessingException {
		
		UserDTO dto = new UserDTO(id, name, lastName, email, password, userPerfil.getValue(), token, datacriacao, dataatualizacao);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	
	}
	
	
	
}
