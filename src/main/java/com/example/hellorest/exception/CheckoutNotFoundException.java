package com.example.hellorest.exception;



public class CheckoutNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1226439803994500725L;

	public CheckoutNotFoundException(String msg){
		super(msg);
	}

}
