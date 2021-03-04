package com.example.demo.repository;

import com.example.demo.model.Checkout;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Repository class for Checkout
 */
@Tag(description = "Checkout from Cart for a customer", name="Checkout")
@RepositoryRestResource(exported = true)
public interface CheckoutRepository extends CrudRepository<Checkout, Long> {
}
