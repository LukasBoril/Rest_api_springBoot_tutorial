package com.example.hellorest.controller;

import com.example.hellorest.exception.CheckoutNotFoundException;
import com.example.hellorest.model.Checkout;
import com.example.hellorest.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/checkouts")
public class CheckoutController {

	private final CheckoutRepository checkoutRepository;

	@Autowired
	public CheckoutController(CheckoutRepository checkoutRepository){
		this.checkoutRepository = checkoutRepository;
	}

	@RequestMapping( value = "/", method = RequestMethod.GET)
	public Iterable<Checkout> list(){
		return checkoutRepository.findAll();
	}

	@RequestMapping( value = "/", method = RequestMethod.POST )
	@ResponseStatus(HttpStatus.CREATED)
	public Checkout create(@RequestBody Checkout checkout){
		return checkoutRepository.save(checkout);
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public Checkout read(@PathVariable(value="id") long id) throws CheckoutNotFoundException {
		Optional<Checkout> checkout = checkoutRepository.findById(id);

		if( checkout.isEmpty() ){
			throw new CheckoutNotFoundException("Checkout with id: " + id + " not found.");
		}
		return checkout.get();
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.PUT )
	public Checkout update(@PathVariable(value="id") long id, @RequestBody Checkout checkout){
		return checkoutRepository.save(checkout);
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
	public void delete(@PathVariable(value="id") long id){
		checkoutRepository.deleteById(id);
	}

	@ExceptionHandler(CheckoutNotFoundException.class)
	public void handleCheckoutNotFound(CheckoutNotFoundException exception, HttpServletResponse response) throws IOException{
		response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
	}

}
