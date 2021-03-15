package com.example.hellorest.repository;


import com.example.hellorest.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void getAdmin() {
        User user = userRepository.findByEmail("admin@admin.ch");
        assertEquals(user.getEmail(), "admin@admin.ch");
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN")));
    }

    @Test
    public void getUser() {
        User user = userRepository.findByEmail("user@user.ch");
        assertEquals(user.getEmail(), "user@user.ch");
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_USER")));
    }
}
