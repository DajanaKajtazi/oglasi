package com.practice.ads.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.practice.ads.model.User;
import com.practice.ads.service.UserService;
import com.practice.ads.web.dto.UserDto;

@Component
public class UserDtoToUser implements Converter<UserDto, User>{

	@Autowired
	private UserService userService;
	
	@Override
	public User convert(UserDto dto) {
		User user=null;
		
		if(dto.getId()!=null)
			user=userService.one(dto.getId());

		if(user==null)
			user=new User();
		
		user.setDateOfBirth(dto.getDateOfBirth());
		user.setEmail(dto.getEmail());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setUsername(dto.getUsername());
		
		return user;
	}

}
