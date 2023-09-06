package com.example.demo.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
			new User("hiptran@gmail.com", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
			new User("user@gmail.com", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));
	
	public UserDetails findUserByEmail(String email) {
		return APPLICATION_USERS
				.stream()
				.filter(u -> u.getUsername().equals(email))	
				.findFirst()
				.orElseThrow();
	}
}
