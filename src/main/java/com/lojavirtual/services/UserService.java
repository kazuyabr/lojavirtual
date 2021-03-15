package com.lojavirtual.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lojavirtual.security.UserSS;

public class UserService {

	public static UserSS authenticaded() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
