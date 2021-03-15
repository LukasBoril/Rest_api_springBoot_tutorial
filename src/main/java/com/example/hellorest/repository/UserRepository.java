package com.example.hellorest.repository;

import com.example.hellorest.model.User;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

}
