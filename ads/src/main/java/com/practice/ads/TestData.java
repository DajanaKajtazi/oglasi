package com.practice.ads;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.practice.ads.model.Advertisement;
import com.practice.ads.model.User;
import com.practice.ads.model.UserRole;
import com.practice.ads.service.AdvertisementService;
import com.practice.ads.service.UserService;

@Component
public class TestData {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		
		String encodedPass;
		
		User user1 = new User();
		user1.setUsername("Ana");
		user1.setFirstName("Anastasia");
		user1.setLastName("Anastasijevic");
		user1.setEmail("ana@mail.com");
		user1.setDateOfBirth(LocalDate.now().minusYears(28));

		encodedPass = passwordEncoder.encode("pass1");
		user1.setPassword(encodedPass);

		user1.setRole(UserRole.ADMIN);

		userService.save(user1);
		
		User user2 = new User();
		user2.setUsername("Boki");
		user2.setFirstName("Bojan");
		user2.setLastName("Bojanic");
		user2.setEmail("boki@mail.com");
		user2.setDateOfBirth(LocalDate.now().minusYears(23));

		encodedPass = passwordEncoder.encode("pass2");
		user2.setPassword(encodedPass);

		user2.setRole(UserRole.USER);

		userService.save(user2);
		
		User user3 = new User();
		user3.setUsername("Vasa");
		user3.setFirstName("Vasilije");
		user3.setLastName("Vasic");
		user3.setEmail("vasa@mail.com");
		user3.setDateOfBirth(LocalDate.now().minusYears(26));

		encodedPass = passwordEncoder.encode("pass3");
		user3.setPassword(encodedPass);

		user3.setRole(UserRole.USER);

		userService.save(user3);
		
		User user4 = new User();
		user4.setUsername("Goga");
		user4.setFirstName("Gordana");
		user4.setLastName("Gordic");
		user4.setEmail("goga@mail.com");
		user4.setDateOfBirth(LocalDate.now().minusYears(21));

		encodedPass = passwordEncoder.encode("pass4");
		user4.setPassword(encodedPass);

		user4.setRole(UserRole.USER);

		userService.save(user4);
	
		
		Advertisement ad1= new Advertisement();
		ad1.setDescription("Huawei p30, kupljen pre godinu dana, funkcionise sve.");
		ad1.setLocation("Subotica");
		ad1.setTitle("Telefon na prodaju");
		ad1.setUpdated("22-1-2021");
		ad1.setAdmin(user2);
		
		advertisementService.save(ad1);
		
		Advertisement ad2= new Advertisement();
		ad2.setDescription("Na prodaju set kuhinjskih nozeva od nerdjajuceg celika.");
		ad2.setLocation("Novi Sad");
		ad2.setTitle("Set kuhinjskih nozeva");
		ad2.setUpdated("12-3-2021");
		ad2.setAdmin(user3);
		
		advertisementService.save(ad2);
		
		Advertisement ad3= new Advertisement();
		ad3.setDescription("Na prodaju automobil u odlicnom stanju.Prodaje se radi odlaska u inostranstvo.");
		ad3.setLocation("Sombor");
		ad3.setTitle("Dacia Sandero");
		ad3.setUpdated("4-2-2021");
		ad3.setAdmin(user4);
		
		advertisementService.save(ad3);
		
		Advertisement ad4= new Advertisement();
		ad4.setDescription("Na prodaju Samsung Galaxy A10.Telefon u perfekntom stanju.");
		ad4.setLocation("Subotica");
		ad4.setTitle("Telefon");
		ad4.setUpdated("3-4-2021");
		ad4.setAdmin(user2);
		
		advertisementService.save(ad4);
		
			
			
			
			
		
	}
}
