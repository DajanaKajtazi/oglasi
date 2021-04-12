package com.practice.ads.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.practice.ads.model.Advertisement;
import com.practice.ads.repository.AdvertisementRepository;
import com.practice.ads.service.AdvertisementService;

@Service
public class JpaAdvertisementService implements AdvertisementService{

	@Autowired
	private AdvertisementRepository advertisementRepository;
	
	@Override
	public Advertisement one(Long id) {
		
		return advertisementRepository.getOne(id);
	}

	@Override
	public Page<Advertisement> all(int pageNum) {
		
		return advertisementRepository.findAll(PageRequest.of(pageNum, 5));
	}

	@Override
	public Advertisement save(Advertisement ad) {
		
		return advertisementRepository.save(ad);
	}

	@Override
	public void delete(Long id) {
		advertisementRepository.deleteById(id);
		
	}

	@Override
	public Page<Advertisement> findByUserId(Long id, int pageNum) {
		
		return advertisementRepository.findByAdminId(id,PageRequest.of(pageNum, 5));
	}

	@Override
	public Page<Advertisement> search(String title, String location, String username, int pageNum) {
		if(title!=null) {
			title="%"+title+"%";
		}
		if(location!=null) {
			location=location+"%";
		}
		
		return advertisementRepository.search(title, location, username, PageRequest.of(pageNum, 5));
	}

}
