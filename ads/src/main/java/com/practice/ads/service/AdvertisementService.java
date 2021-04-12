package com.practice.ads.service;

import org.springframework.data.domain.Page;


import com.practice.ads.model.Advertisement;

public interface AdvertisementService {

	Advertisement one(Long id);
	Page<Advertisement> all(int pageNum);
	Advertisement save(Advertisement ad);
	void delete(Long id);
	Page<Advertisement> findByUserId(Long id, int pageNum);
	Page<Advertisement> search(String title,String location,String username, int pageNum);
}
