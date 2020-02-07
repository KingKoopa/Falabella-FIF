package com.falabella.fif.bender.beermaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	public NotFoundException(String msg){
        super(msg);
    }
}
