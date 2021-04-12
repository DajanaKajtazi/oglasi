package com.practice.ads.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.practice.ads.model.User;
import com.practice.ads.security.TokenUtilis;
import com.practice.ads.service.AdvertisementService;
import com.practice.ads.service.UserService;
import com.practice.ads.support.AdvertisementDtoToAdvertisement;
import com.practice.ads.support.AdvertisementToAdvertisementDto;
import com.practice.ads.support.UserDtoToUser;
import com.practice.ads.support.UserToUserDto;
import com.practice.ads.web.dto.AdvertisementDto;
import com.practice.ads.web.dto.LoginDto;
import com.practice.ads.web.dto.UserDto;
import com.practice.ads.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("api/users")
public class ApiUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserToUserDto toDto;
	@Autowired
	private UserDtoToUser toUser;
	
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private AdvertisementToAdvertisementDto adToDto;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TokenUtilis tokenUtilis;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
		
		User user=userService.one(id);
		
		if(user!=null) {
			return new ResponseEntity<UserDto>(toDto.convert(user), HttpStatus.OK);
		}else {
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAll(@RequestParam(defaultValue = "0") int page){
		Page<User> usersPage=userService.all(page);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Total-Pages", Integer.toString(usersPage.getTotalPages()));
		
		return new ResponseEntity<List<UserDto>>(toDto.convert(usersPage.getContent()),headers ,HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<UserDto> add(@RequestBody @Validated UserRegistrationDto registrationDto) {
		
		if(registrationDto.getPassword()==null || registrationDto.getPassword().isEmpty()
				|| !registrationDto.getPassword().equals(registrationDto.getPasswordConfirm())) {
			return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
		}
		
		User toAdd=toUser.convert(registrationDto);
		toAdd.setPassword(registrationDto.getPassword());
		
		User user=userService.save(toAdd);
		return new ResponseEntity<UserDto>(toDto.convert(user),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> edit(@PathVariable Long id,@RequestBody @Validated UserDto dto) {
		
		if(!id.equals(dto.getId())) {
			return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
		}
		
		User toEdit=toUser.convert(dto);
		User user=userService.save(toEdit);
		
		return new ResponseEntity<UserDto>(toDto.convert(user),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto dto) {
		
		UsernamePasswordAuthenticationToken authenticationToken= 
				new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
		Authentication authentication=authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		try {
			UserDetails userDetails=userDetailsService.loadUserByUsername(dto.getUsername());
			return ResponseEntity.ok(tokenUtilis.generateToken(userDetails));
		}catch (UsernameNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}/advertisements")
	public ResponseEntity<List<AdvertisementDto>> getUsersAds(@PathVariable Long id,
			@RequestParam(defaultValue = "0") int page){
		Page<Advertisement> advertsPage=advertisementService.findByUserId(id, page);
		List<Advertisement> adverts=advertsPage.getContent();
		
		
		return new ResponseEntity<List<AdvertisementDto>>(adToDto.convert(adverts), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle(){
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
