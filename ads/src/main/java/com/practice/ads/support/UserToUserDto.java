package com.practice.ads.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.practice.ads.model.User;
import com.practice.ads.web.dto.UserDto;

@Component
public class UserToUserDto implements Converter<User, UserDto>{

	@Override
	public UserDto convert(User user) {
		
		UserDto dto=new UserDto();
		
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setDateOfBirth(user.getDateOfBirth());
		dto.setEmail(user.getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		
		return dto;
	}
	
	public List<UserDto> convert(List<User> users){
		List<UserDto> dto=new ArrayList<>();
		
		for(User user:users) {
			dto.add(convert(user));		
		}
		
		return dto;
	}

}
