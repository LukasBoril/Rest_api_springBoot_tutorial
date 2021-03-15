package com.example.hellorest;

import com.example.hellorest.model.Checkout;
import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CheckoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckoutRepositoryTest {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Test
    public void saveCheckout() {
        Checkout checkout= new Checkout();
        Customer customer1= new Customer();
        customer1.setFirstname("John");
        customer1.setLastname("Doe");

        checkout.setCustomer(customer1);
        checkoutRepository.save(checkout);
    }
}
