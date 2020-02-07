package com.falabella.fif.bender.beermaster.service;

import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.dto.BoxPriceDTO;

public interface BeerService {
	
	public BeerItemDTO saveBeer(BeerItemDTO beerItemDTO) throws Exception;
	
	public BoxPriceDTO calculateBoxPriceById(Long beerId, String currency, Integer quantity) throws Exception;
	
}
