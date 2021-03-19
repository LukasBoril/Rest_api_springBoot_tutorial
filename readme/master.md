# Master: Create the Initial Project Setup

| [master]()
| [database-bootstrap](database-bootstrap.md)
| [flyway](flyway.md)
| [liquibase](liquibase.md)
| [profiles](profiles.md)
| [docker](docker.md)
| [rest](rest.md)
| [security-step-1](security-step-1.md)
| [security-step-2]()
|


[Go to master branch](https://github.zhaw.ch/bacn/ase2-spring-boot-hellorest/tree/master)

- [https://spring.io/guides](https://spring.io/guides)
- [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- [https://docs.spring.io/spring-boot/docs/2.4.4/reference/html/](https://docs.spring.io/spring-boot/docs/2.4.4/reference/html/)

<br/>

The tutorial consists of the following hands-on:

- [Hands-on 0: Use _Spring Initializer_ in _IntelliJ_ (Ultimate Edition)](#hands-on-0-use-spring-initializer-in-intellij-ultimate-edition).
- [Hands-on 1: First test after initial project setup](#hands-on-1-first-test-after-initial-project-setup).
- [Hands-on 2: Create Models and Repositories](#hands-on-2-create-models-and-repositories).

<br/>

## Hands-on 0: Use Spring Initializer in IntelliJ (Ultimate Edition)

In the tutorial _Hands-on 0_ we are generating a new _spring-boot project_ by
using the _built-in spring initializr_ in _IntelliJ Ultimate_.

_IntelliJ IDEA_ provides the _Spring Initializr_ project wizard that integrates with the _Spring Initializr
API_ to generate and import your project directly from the _IDE_.

_Spring Initializr_ generates a valid project structure with the following files:

- A build configuration file, for example, _build.gradle_ for _Gradle_ or _pom.xml_ for _Maven_.
- A class with the _main()_ method to bootstrap the application.
- An empty _JUnit_ test class.
- An empty Spring application configuration file: _application.properties_

Choose Java SDK (can 8, 11, 14, etc):

<br/>

![create-project-step-1.png](create-project-step-1.png)

<br/>

Choose group and artifact id, build automation, language and packaging, project name, description and package

<br/>

![create-project-step-2](create-project-step-2.png)

<br/>

Choose the dependencies like _Spring Web_ and _SQL H2 Data Base_:

<br/>

![create-project-step-3.png](create-project-step-3.png)

<br/>

![create-project-step-4.png](create-project-step-4.png)

<br/>

Choose project name and folder:

<br/>

![create-project-step-5.png](create-project-step-5.png)

<br/>

The following artifacts were created:
<br/>

![create-project-generated-artifacts.png](create-project-generated-artifacts.png)

<br/>

The spring-boot plugin allows to start the project.

<br/>

![create-project-spring-boot-plugin](create-project-spring-boot-plugin.png)

<br/>


## Hands-on 1: First test after initial project setup

In the tutorial _Hands-on 1_ we are preparing a HelloWorldApplication class providing
the boot of the application and a simple end point on **/** returning _Hello World_.
Do not forget annotating the class with _@SpringBootApplication_ and _@Controller_.

###  HelloWorldApplication Klasse

<br/>

```java
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World";
    }


}

```

<br/>

Run the application:

<br/>

![create-project-run.png](create-project-run.png)

<br>


### HelloControllerTest Klasse

The _HelloControllerTest_ class is checking if the endpoint **/** is providing _Hello World_.
Spring has an easy prepared class _MockMvc_. With the help of the class _@AutoConfigureMockMvc_ we
get an auto configure of the _MockMvc_. The annotation _@SpringBootTest_ does make sure
we get a completly configured spring-boot application for testing.  
The test type is an integration test.

<br/>

```java
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
    }
}
```

<br/>

Run the unit test:

<br/>

![create-project-run-unit-test.png](create-project-run-unit-test.png)

<br/>


## Hands-on 2: Create Models and Repositories

The _hands-on 2_ tutorial is using two model classes and two repository interfaces ot provide
automatically exposed end points.

<br/>

### Project Structure

Create the project structure with a package for model and repository classes.

<br/>

![project-structure.png](project-structure.png)

<br/>


### Design Class Diagramm of the Spring Data Rest Example

With the two model classes and the two repository inferfaces we can expose automatically the rest
end points with crud functionality

- /customers
- /checkouts

<br/>

![master-dcd.png](master-dcd.png)

<br/>


### Pom with dependencies for Open Api

The dependency _springdoc-openapi-data-rest_ is exposing automatically rest end points.
The dependency _springdoc-openapi-data-rest_ is documenting the exposed end-points to OpenApi.

<br/>

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-ui</artifactId>
  <version>1.5.5</version>
</dependency>

<!-- Open API for Automatic Rest Interfaces -->
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-data-rest</artifactId>
  <version>1.5.5</version>
</dependency>
```

<br/>

### Application Start

The _HelloRestApplication_ class is booting the application. The Method afterInit is writing
the links for the data and the tools:

- H2 console
- OpenApi web front end
- The OpenApi api docs in json or yaml format

The bean _customOpenAPI_ is configuring the _OpenApi_ dependency. In following instruction
_spring-security-step-2_ we will move this code to an enhanced configuration class

<br/>

```java
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HelloRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloRestApplication.class, args);
    }

    @PostConstruct
    public void afterInit() {
        System.out.println("\n\nEnter in Browser:\nhttp://localhost:8080 \n" +
                "http://localhost:8080/v3/api-docs\n" +
                "http://localhost:8080/v3/api-docs.yaml -> yaml file is downloaded -> https://editor.swagger.io/\n" +
                "http://localhost:8080/swagger-ui.html \n" +
                "http://localhost:8080/h2-console  " + "" +
                "-> mit Generic H2 (Embedded), org.h2.Driver, jdbc:h2:mem:testdb und sa \n\n");
    }
    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Customer and Checkout API for ASE").version(appVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

```

<br/>

### Application Properties (in Resources)

The application properties are configuring some of the included dependencies like:

- The H2 data base in the memory with the name _jdbc:h2:mem:testdb
- The url of the OpenApi end point visualization

<br/>

```
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

springdoc.swagger-ui.path=/swagger-ui.html

springdoc.version= @springdoc.version@
```
<br/>

### Model Class Customer

The Customer model class is a JPA entity. The id is generated by the database. The model
consists beside of the id of 2 properties:

- firstname
- lastname

<br/>

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Model class for customer
 */
@Entity
public class Customer {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
```
<br/>

### Model Class Checkout

The _Checkout_ model class is a JPA entity. The _id_ is generated by the database. The model
consists beside of the _id_ of one property, which is a _one-to-one relation_ to the _Customer entity_.
In the database we will get a _foreign key_ in the _checkout table_ pointing to the _customer table_.

<br/>

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Model class for checkout
 */
@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
```

<br/>

### Repositories

### CustomerRepository

The _CustomerRepository_ will be automatically exposed by the _spring-starter-data-rest_ under
the end point _/customers_.

<br/>

```java
/**
 * Repository class for Customer
 */
//@RepositoryRestResource(exported = false)
@RepositoryRestResource(path = "customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
```

<br/>

### CheckoutRepository

The _CheckoutRepository_ will be automatically exposed by the _spring-starter-data-rest_ under
the end point _/checkouts_.

<br/>

```java
/**
 * Repository class for Checkout
 */

public interface CheckoutRepository extends CrudRepository<Checkout, Long> {
}
```

<br/>

## Testing

The tests are from its structure not unit tests. Since we are using the annotiation
_@SpringBootTest_ we are always starting the whole spring-boot application with all available
features like dependency injection.

<br/>

![master-test-dcd.png](master-test-dcd.png)

<br/>

The class diagram shows that the integration test is using an _AbstractTest_ class to provide
commonly used functionality.

### AbstractTest

The _AbstractTest_ class contains the setup of the WebApplicationContext and some  
JSON mapper's for normal JSON-Object and the specific HAL embedded JSON Objects.

<br/>

```java

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, clazz);
    }

    protected String extractEmbeddedFromHalJson(String content, String attribute) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = new HashMap<>();
            map = mapper.readValue(content, new TypeReference<HashMap<String,Object>>(){});
            Map<String,Object> embedded = (Map<String, Object>) map.get("_embedded");
            List<Object> customers = (List<Object>) embedded.get(attribute);
            return mapToJson(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
```

<br/>

### CustomerRestControllerTest

The _CustomerRestControllerTest_ class is verifying_

- getting a list of customers
- getting one customer
- posting a new customer

<br/>

```java
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CustomerRestControllerTest extends AbstractTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer1;
    Customer customer2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        customer1= new Customer();
        customer1.setFirstname("Felix");
        customer1.setLastname("Muster");
        customerRepository.save(customer1);
        customer2= new Customer();
        customer2.setFirstname("Max");
        customer2.setLastname("Mustermann");
        customerRepository.save(customer2);
    }

    @Test
    public void getCustomersList() throws Exception {
        String uri = "/customers";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();

        String content = extractEmbeddedFromHalJson(response,"customers");
        Customer[] customerList = super.mapFromJson(content, Customer[].class);
        assertTrue(customerList.length > 0);
        assertEquals(customerList[0].getFirstname(), customer1.getFirstname());
        assertEquals(customerList[1].getFirstname(), customer2.getFirstname());

    }

    @Test
    public void getOneCustomer() throws Exception {
        String uri = "/customers/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();
        Customer customer = super.mapFromJson(response, Customer.class);
        assertEquals(customer.getFirstname(), customer1.getFirstname());
    }

    @Test
    public void postOneCustomer() throws Exception {
        String uri = "/customers";

        Customer customer= new Customer();
        customer.setFirstname("John");
        customer.setLastname("Doe");

        String json = super.mapToJson(customer);

        MvcResult postMvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE, "application/hal+json")
                .content(json))
                .andReturn();

        int status = postMvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String response = postMvcResult.getResponse().getContentAsString();
        Customer postCustomer = super.mapFromJson(response, Customer.class);
        assertEquals(postCustomer.getFirstname(), customer.getFirstname());
    }

}
```
<br/>

## Test H2 Database

With the help of the _h2-console_ we can check the content of the database. The connection
url is defined by the entry in the _application.properties_ file.

<br/>

Enter in Browser: http://localhost:8080/h2-console

<br/>

![check-h2-database.png](check-h2-database.png)

<br/>

After connection to the console you can see all generated tables.

<br/>

![h2-database.png](h2-database.png)

<br/>

## Working with Postman

You can download postman from [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

<br/>

Create a get request:

<br/>

![postman-get-request.png](postman-get-request.png)

<br/>

Create a post request:

<br/>

![postman-post-request.png](postman-post-request.png)

<br/>
