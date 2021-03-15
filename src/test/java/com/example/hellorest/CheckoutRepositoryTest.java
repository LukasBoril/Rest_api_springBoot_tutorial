package com.example.hellorest;

import com.example.hellorest.model.Checkout;
import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CheckoutRepository;
import com.example.hellorest.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CheckoutRepositoryTest {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void saveCheckout() {
        Checkout checkout= new Checkout();
        Customer customer1= new Customer();
        customer1.setFirstname("John");
        customer1.setLastname("Muster");

        checkout.setCustomer(customer1);
        checkoutRepository.save(checkout);

        Customer customer = customerRepository.findByFirstnameAndLastname("John", "Muster");
        assertEquals(customer.getFirstname(), customer1.getFirstname());
    }
}
