package com.delivery.tiago.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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

import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.model.UserPerfil;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.service.user.UserService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class UserServiceTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repository;
	
	static final String EMAIL = "email@test.com";
	
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(User.class)))
			.willReturn(getMockUser());
		User response = service.save(new User());
		
		assertNotNull(response);
	}
	

	@Test
	@Order(2)
	public void testFindByEmail(){
		
		BDDMockito.given(repository.findByEmail(Mockito.anyString()))
			.willReturn(Optional.ofNullable(new User()));

		Optional<User> response = service.findByEmail(EMAIL);
		assertTrue(!response.isEmpty());
	}
	

	private User getMockUser() {
		return new User(2, "Test User", "Test Sobrenome", "123", EMAIL, UserPerfil.ADMIN, LocalDateTime.now(), LocalDateTime.now() );
	}
	

	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}


}
