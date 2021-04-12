package com.practice.ads.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.ads.model.Advertisement;
import com.practice.ads.service.AdvertisementService;
import com.practice.ads.support.AdvertisementDtoToAdvertisement;
import com.practice.ads.support.AdvertisementToAdvertisementDto;
import com.practice.ads.web.dto.AdvertisementDto;

@RestController
@RequestMapping("api/advertisements")
public class ApiAdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private AdvertisementDtoToAdvertisement toAd;
	@Autowired
	private AdvertisementToAdvertisementDto toDto;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<AdvertisementDto> getOne(@PathVariable Long id){
		
		Advertisement advert=advertisementService.one(id);
		if(advert!=null)
			return new ResponseEntity<AdvertisementDto>(toDto.convert(advert), HttpStatus.OK);
		else
			return new ResponseEntity<AdvertisementDto>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<AdvertisementDto>> getAll(
			@RequestParam(value = "title",required = false) String title,
			@RequestParam(value = "location",required = false) String location,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "pageNum",defaultValue = "0") int page){
		
		Page<Advertisement> adverts=null;
		if(title!=null || location!=null || username!=null) {
			adverts=advertisementService.search(title, location, username, page);
		}else {
			adverts=advertisementService.all(page);
		}
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Total-Pages", Integer.toString(adverts.getTotalPages()));
		
		return new ResponseEntity<List<AdvertisementDto>>(toDto.convert(adverts.getContent()), headers ,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		Advertisement advert=advertisementService.one(id);
		if(advert!=null) {
			advertisementService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<AdvertisementDto> add(@RequestBody @Validated AdvertisementDto dto){
		
		Advertisement advert=advertisementService.save(toAd.convert(dto));
		
		return new ResponseEntity<AdvertisementDto>(toDto.convert(advert), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AdvertisementDto> edit(@PathVariable Long id,@RequestBody AdvertisementDto dto){
		
		if(!id.equals(dto.getId())) {
			return new ResponseEntity<AdvertisementDto>(HttpStatus.BAD_REQUEST);
		}
		Advertisement advert=advertisementService.save(toAd.convert(dto));
		
		return new ResponseEntity<AdvertisementDto>(toDto.convert(advert), HttpStatus.OK);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle(){
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
