package com.example.hellorest.client;

import com.example.hellorest.auth.TokenAuthenticationService;
import com.example.hellorest.model.Customer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class SpringRestClient {

    private TokenAuthenticationService.AuthHeaderName authHeaderName = TokenAuthenticationService.AuthHeaderName.xauth;

    public final String REST_SERVICE_URI = "http://localhost:8080";
    private final String json = "{\"email\":\"admin@admin.ch\", \"password\":\"admin\"}";
    private String authToken;
    private long createId;
    private final Properties properties = new Properties();

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send Post-credentials.
     */
    private HttpHeaders getHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(authHeaderName.getHeaderName(), authToken);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private void login() {

        System.out.println("\nTesting login API-----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(json);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI + "/api/login", HttpMethod.POST, request, String.class);
        authToken = response.getHeaders().get(authHeaderName.getHeaderName()).get(0);
        System.out.println("Received Login Token: " + authHeaderName.getHeaderName() + ": " + authToken);
    }

    /*
     * Send a GET request to get list of all Posts.
     */
    private void listAllCustomers() {

        System.out.println("\nTesting listAllPosts API-----------");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List<Customer>> response = restTemplate.exchange(REST_SERVICE_URI + "/api/customers/", HttpMethod.GET, request, new ParameterizedTypeReference<List<Customer>>() {
        });

        List<Customer> customers = response.getBody();

        if (customers != null) {
            System.out.println(customers.stream()
                    .map(user -> user.getFirstname() + " " + user.getLastname())
                    .collect(Collectors.toList()));
        } else {
            System.out.println("No customer exist----------");
        }
    }

    /*
     * Send a GET request to get a specific Post.
     */
    private void getCustomer() {

        System.out.println("\nTesting getCustomer API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Customer> response = restTemplate.exchange(REST_SERVICE_URI + "/api/customers/1", HttpMethod.GET, request, Customer.class);
        Customer customer = response.getBody();
        System.out.println(customer);
    }

    /*
     * Send a POST request to create a new Post.
     */
    private void createCustomer() {

        System.out.println("\nTesting create Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = createCustomer1();
        HttpEntity<Object> request = new HttpEntity<Object>(customer, getHeaders());
        ResponseEntity<Customer> response = restTemplate.exchange(REST_SERVICE_URI + "/api/customers/", HttpMethod.POST, request, Customer.class);
        createId = response.getBody().getId();
        System.out.println(response.getBody());
    }


    /*
     * Send a PUT request to update an existing Post.
     */
    private void updateCustomer() {

        System.out.println("\nTesting update Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = createCustomer2();
        HttpEntity<Object> request = new HttpEntity<Object>(customer, getHeaders());
        ResponseEntity<Customer> response = restTemplate.exchange(REST_SERVICE_URI + "/api/customers/1", HttpMethod.PUT, request, Customer.class);
        System.out.println(response.getBody());
    }

    /*
     * Send a DELETE request to delete a specific Post.
     */
    private void deleteCustomer() {

        System.out.println("\nTesting delete Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI + "/api/customers/" + createId, HttpMethod.DELETE, request, Customer.class);
    }

    private Customer createCustomer1() {

        Customer customer1 = new Customer();
        customer1.setFirstname("Hans");
        customer1.setLastname("Baumgartner");

        return customer1;

    }

    private Customer createCustomer2() {

        Customer customer2 = new Customer();
        customer2.setFirstname("Max");
        customer2.setLastname("Müller");

        return customer2;

    }

    public static void main(String[] args) {

        new SpringRestClient().run();
    }

    public void run() {

        loadProperties();

        // support for X-AUTH_TOKEN or AUTHORIZATION with Bearer
        if (properties.getProperty("hellorest.auth.headername").contains("bearer")) {
            authHeaderName = TokenAuthenticationService.AuthHeaderName.bearer;
        }

        login();

        if (authToken == null) {
            System.out.println("no TOKEN from login");
            System.exit(0);
        }

        listAllCustomers();

        getCustomer();

        createCustomer();
        listAllCustomers();

        updateCustomer();
        listAllCustomers();

        deleteCustomer();
        listAllCustomers();

        System.out.println("abgeschlossen");

    }

    /**
     * Load the application properties
     */
    private void loadProperties() {
        try (InputStream iStream = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {

            if (iStream == null) {
                throw new IOException("File not found");
            }
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
