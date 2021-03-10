package com.example.hellorest.repository;

import com.example.hellorest.model.Checkout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Repository class for Checkout
 */
@RepositoryRestResource(exported = true)
public interface CheckoutRepository extends CrudRepository<Checkout, Long> {
}
