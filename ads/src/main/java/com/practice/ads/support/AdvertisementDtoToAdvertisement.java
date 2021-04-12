package com.practice.ads.support;

import javax.persistence.Convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.practice.ads.model.Advertisement;
import com.practice.ads.model.User;
import com.practice.ads.service.AdvertisementService;
import com.practice.ads.service.UserService;
import com.practice.ads.web.dto.AdvertisementDto;
@Component
public class AdvertisementDtoToAdvertisement implements Converter<AdvertisementDto, Advertisement>{

	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Advertisement convert(AdvertisementDto dto) {
		User user=userService.one(dto.getAdminId());
		if(user!=null) {
			Advertisement advert=null;
			if(dto.getId()!=null) {
				advert=advertisementService.one(dto.getId());
			}else {
				advert=new Advertisement();
			}
			
			advert.setAdmin(user);
			advert.setDescription(dto.getDescription());
			advert.setTitle(dto.getTitle());
			advert.setLocation(dto.getLocation());
			advert.setUpdated(dto.getUpdated());
			
			return advert;
		}else {
			throw new IllegalStateException("Trying to attach to non-existant entities");
		}
	}

}
