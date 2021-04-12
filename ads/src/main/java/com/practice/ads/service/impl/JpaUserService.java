package com.practice.ads.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.practice.ads.model.User;
import com.practice.ads.repository.UserRepository;
import com.practice.ads.service.UserService;

@Service
public class JpaUserService implements UserService{

	@Autowired
	private UserRepository userRepositroy;
	
	@Override
	public User one(Long id) {
		
		return userRepositroy.getOne(id);
	}

	@Override
	public Page<User> all(int pageNum) {
		
		return userRepositroy.findAll(PageRequest.of(pageNum, 10));
	}

	@Override
	public User save(User user) {

		return userRepositroy.save(user);
	}

	@Override
	public void delete(Long id) {

		userRepositroy.deleteById(id);
	}

	@Override
	public User getByUsername(String username) {
		
		return userRepositroy.findByUsername(username);
	}



}
