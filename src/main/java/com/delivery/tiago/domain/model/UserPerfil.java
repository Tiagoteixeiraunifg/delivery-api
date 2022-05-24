package com.delivery.tiago.domain.model;


public enum UserPerfil {
	
	ADMIN("ADMIN"), 
	USUARIO("USUARIO");
	
	
	private String value;
	
	private UserPerfil(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
