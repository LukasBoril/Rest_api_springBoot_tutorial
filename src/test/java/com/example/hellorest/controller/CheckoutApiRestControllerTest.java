package com.example.hellorest.controller;

import com.example.hellorest.model.Checkout;
import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CheckoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutApiRestControllerTest extends AbstractTest {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getCheckoutsList() throws Exception {
        String uri = "/api/checkouts/";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();

        Checkout[] checkoutList = super.mapFromJson(response, Checkout[].class);
        assertTrue(checkoutList.length > 0);
        assertEquals(checkoutList[0].getCustomer().getFirstname(), "Max");
        assertEquals(checkoutList[1].getCustomer().getFirstname(), "John");

    }

    @Test
    public void getOneCheckout() throws Exception {
        String uri = "/api/checkouts/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();
        Checkout checkout = super.mapFromJson(response, Checkout.class);
        assertEquals(checkout.getCustomer().getFirstname(), "Max");
    }

    @Test
    public void postOneCheckout() throws Exception {
        String uri = "/api/checkouts/";

        Checkout checkout= new Checkout();
        Customer customer1= new Customer();
        customer1.setFirstname("John");
        customer1.setLastname("Doe");

        checkout.setCustomer(customer1);

        String json = super.mapToJson(checkout);

        MvcResult postMvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andReturn();

        int status = postMvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String response = postMvcResult.getResponse().getContentAsString();
        Checkout postCheckout = super.mapFromJson(response, Checkout.class);
        assertEquals(postCheckout.getCustomer().getFirstname(), customer1.getFirstname());
    }

}
