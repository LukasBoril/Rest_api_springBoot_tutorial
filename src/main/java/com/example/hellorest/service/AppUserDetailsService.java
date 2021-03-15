package com.example.hellorest.service;

import com.example.hellorest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AppUserDetailsService extends UserDetailsService{

	public User findByEmail(String email);

}
