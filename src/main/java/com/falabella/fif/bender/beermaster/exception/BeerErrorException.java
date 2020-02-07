package com.falabella.fif.bender.beermaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class BeerErrorException extends Exception {

	public BeerErrorException(String error){
        super(error);
    }
	
}
