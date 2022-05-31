package com.delivery.tiago.util;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import com.delivery.tiago.common.UtilApi;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UtilApiTest {
	
	static final String EMAIL = "TIAGO@TIAGO.COM";
	static final String EMAILERROR = "TIAGO@";
	static UtilApi utilApi;
	
	@BeforeAll
	private void setup() {
		utilApi = new UtilApi();
	}
	
	
	
	@Test
	@Order(1)
	public void emailTest() {
		
		boolean resultado = false;
		resultado = utilApi.isValidEmailAddressRegex(EMAIL);
		
		assertTrue(resultado);
		
	}
	
	@Test
	@Order(2)
	public void emailTestError() {
		
		boolean resultado = true;
		resultado = utilApi.isValidEmailAddressRegex(EMAILERROR);
		
		assertFalse(resultado);
		
	}
	
	
	
	
}
