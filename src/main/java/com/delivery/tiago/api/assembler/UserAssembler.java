package com.delivery.tiago.api.assembler;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.delivery.tiago.api.model.output.dto.UserDTO;
import com.delivery.tiago.domain.model.User;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class UserAssembler {
	
	
private ModelMapper modelMapper;
	
	public UserDTO toModel(User user) {	
		return modelMapper.map(user, UserDTO.class);
	}
	
	
	public UserDTO toModelOptional(Optional<User> user) {
		User usr = null;
		if(user.isPresent()) {
			usr = user.get();
		}
		return modelMapper.map(usr, UserDTO.class);
	}
	
	public User toModel(Optional<User> user) {
		User usr = null;
		if(user.isPresent()) {
			usr = user.get();
		}
		return modelMapper.map(usr, User.class);
	}
	
	
	public List<UserDTO> toListUserDTO(List<User> listUser){
		return  listUser.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	
	
}
