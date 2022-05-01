package com.delivery.tiago.domain.service.user;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.delivery.tiago.api.assembler.UserAssembler;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
	

	private UserRepository repository;
	private UserAssembler assembler;
	
	

	public Optional<User> findByEmail(String email){
		return repository.findByEmail(email);
	} 
	
	
	public UserDTO findByEmailTDO(String email) {
		
		return assembler.toModelOptional(repository.findByEmail(email));
		
	}
	
	@Transactional
	public User save(User user) {
		return repository.save(user);
	}
	
	

}
