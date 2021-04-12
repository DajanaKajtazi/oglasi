package com.practice.ads.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.practice.ads.model.Advertisement;
import com.practice.ads.web.dto.AdvertisementDto;

@Component
public class AdvertisementToAdvertisementDto implements Converter<Advertisement, AdvertisementDto> {

	@Override
	public AdvertisementDto convert(Advertisement advertisement) {
		AdvertisementDto dto=new AdvertisementDto();
		
		dto.setId(advertisement.getId());
		dto.setAdminId(advertisement.getAdmin().getId());
		dto.setAdminUsername(advertisement.getAdmin().getUsername());
		dto.setDescription(advertisement.getDescription());
		dto.setLocation(advertisement.getLocation());
		dto.setTitle(advertisement.getTitle());
		dto.setUpdated(advertisement.getUpdated());
		
		return dto;
	}
	
	public List<AdvertisementDto> convert(List<Advertisement> advertisements){
		List<AdvertisementDto> dto=new ArrayList<>();
		
		for(Advertisement advert:advertisements) {
			dto.add(convert(advert));
		}
		return dto;
	}

}
