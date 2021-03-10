package com.example.hellorest.exception;



public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1226439803994500725L;

	public CustomerNotFoundException(String msg){
		super(msg);
	}

}
