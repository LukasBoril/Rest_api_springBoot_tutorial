package com.example.hellorest.service;


import com.example.hellorest.service.AppUserDetailsService;
import com.example.hellorest.service.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppUserDetailsServiceTest {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @Test
    public void loadUser() {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl)appUserDetailsService.loadUserByUsername("admin@admin.ch");
        assertEquals(userDetailsImpl.getUsername(),"admin@admin.ch");

    }
}
