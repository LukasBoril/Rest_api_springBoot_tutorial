package com.example.hellorest.controller;

import com.example.hellorest.exception.CustomerNotFoundException;
import com.example.hellorest.model.Customer;
import com.example.hellorest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}

	@RequestMapping( value = "/", method = RequestMethod.GET )
	public Iterable<Customer> list(){
		return customerRepository.findAll();
	}

	@RequestMapping( value = "/", method = RequestMethod.POST )
	public Customer create(@RequestBody Customer customer){
		return customerRepository.save(customer);
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public Customer read(@PathVariable(value="id") long id) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepository.findById(id);

		if( customer.isEmpty() ){
			throw new CustomerNotFoundException("Customer with id: " + id + " not found.");
		}
		return customer.get();
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.PUT )
	public Customer update(@PathVariable(value="id") long id, @RequestBody Customer customer){
		return customerRepository.save(customer);
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
	public void delete(@PathVariable(value="id") long id){
		customerRepository.deleteById(id);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public void handleCustomerNotFound(CustomerNotFoundException exception, HttpServletResponse response) throws IOException{
		response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
	}

}
