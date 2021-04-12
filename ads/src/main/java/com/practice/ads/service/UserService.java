package com.practice.ads.service;



import org.springframework.data.domain.Page;

import com.practice.ads.model.User;

public interface UserService {

	User one(Long id);
	Page<User> all(int pageNum);
	User save(User user);
	void delete(Long id);
	User getByUsername(String username);
	
}
