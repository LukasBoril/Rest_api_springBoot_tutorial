package com.example.hellorest;

import com.example.hellorest.model.Checkout;
import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CheckoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutRestControllerTest extends AbstractTest {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

    }

    @Test
    public void getCheckoutsList() throws Exception {
        String uri = "/checkouts";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();

        String content = extractEmbeddedFromHalJson(response,"checkouts");
        Checkout[] checkoutList = super.mapFromJson(content, Checkout[].class);
        assertTrue(checkoutList.length > 0);


    }

    @Test
    public void getOneCheckout() throws Exception {
        String uri = "/checkouts/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();
        Checkout checkout = super.mapFromJson(response, Checkout.class);
        assertNotNull(checkout);
    }

    @Test
    public void postOneCheckout() throws Exception {
        String uri = "/checkouts";

        Checkout checkout= new Checkout();
        Customer customer1= new Customer();
        customer1.setFirstname("John");
        customer1.setLastname("Doe");
        checkout.setCustomer(customer1);

        String json = super.mapToJson(checkout);

        MvcResult postMvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")
                .content(json))
                .andReturn();

        int status = postMvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String response = postMvcResult.getResponse().getContentAsString();
        Checkout postCheckout = super.mapFromJson(response, Checkout.class);
        assertNotNull(checkout);
    }

}
