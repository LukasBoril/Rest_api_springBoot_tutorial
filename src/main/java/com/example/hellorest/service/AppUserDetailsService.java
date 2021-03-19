package com.example.hellorest.service;

import com.example.hellorest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AppUserDetailsService extends UserDetailsService{

	User findByEmail(String email);

}
