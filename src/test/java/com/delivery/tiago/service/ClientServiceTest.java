package com.delivery.tiago.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.delivery.tiago.domain.model.Cliente;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.repository.ClienteRepository;
import com.delivery.tiago.domain.service.cliente.ClienteService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClientServiceTest {
	
	@Autowired
	private ClienteService service;

	@MockBean
	private ClienteRepository repository;
	
	
	static final String EMAIL = "email@test.com";
	static final Integer ID_USER = 1;
	static final Integer ID_CLIENT = 2;
	
	
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Cliente.class)))
			.willReturn(getMockCliente());
		Cliente response = service.saveCliente(new Cliente());
		
		assertNotNull(response);
	}
	
	@Test
	@Order(2)
	public void testFindByEmail(){
		
		BDDMockito.given(repository.findByEmail(Mockito.anyString()))
			.willReturn(Optional.ofNullable(new Cliente()));

		Optional<Cliente> response = service.findByEmail(EMAIL);
		assertTrue(!response.isEmpty());
		
	}
	
	@Test
	@Order(3)
	public void testFindByIdAndUserId() {
		BDDMockito.given(repository.findByIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(Optional.ofNullable(new Cliente()));
		
		Optional<Cliente> response = service.findByIdAndUserId(ID_CLIENT, ID_USER);
		
		assertTrue(!response.isEmpty());
	}
	
	
	
	
	
	private User getMockUser() {
		return new User(2, "Test User", "Test Sobrenome", "123", EMAIL, UserPerfil.ADMIN );
	}
	
	
	private Cliente getMockCliente() {
		return new Cliente(1,getMockUser(), "Test User", "Test Sobrenome", "030.861.705.55",
							"77991880000", EMAIL, "test rua", "test complemento","guanambi",
							"123","bahia","46430000"  );
	}
	

	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	

}
