package com.practice.ads.web.dto;

import javax.validation.constraints.NotBlank;

public class UserRegistrationDto extends UserDto{
	
	@NotBlank
	private String password;
	@NotBlank
	private String passwordConfirm;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	
}
