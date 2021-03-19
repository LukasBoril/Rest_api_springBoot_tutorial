package com.example.hellorest.repository;


import com.example.hellorest.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



/**
 * Repository class for Customer
 */
@RepositoryRestResource(exported = true, path = "customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByFirstnameAndLastname(String firstname, String lastname);
}
