package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtUtils;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.AuthenticationRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private AuthenticationManager authenticationManager;
	private UserDao userDao;
	private JwtUtils jwtUtils;
	
	@PostMapping("authenticate")
	public ResponseEntity<String> authenticate(
			@RequestBody AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final UserDetails user = userDao.findUserByEmail(request.getEmail());
		if(user != null) {
			return ResponseEntity.ok(jwtUtils.generateToken(user));
		}
		return ResponseEntity.status(400).body("Some error has occurred");
	}
}
