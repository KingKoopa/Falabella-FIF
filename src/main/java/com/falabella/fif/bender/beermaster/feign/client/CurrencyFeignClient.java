package com.falabella.fif.bender.beermaster.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falabella.fif.bender.beermaster.feign.client.response.CurrencyResponseDTO;

//@FeignClient(url = "http://api.currencylayer.com/convert")
public interface CurrencyFeignClient {


	@GetMapping()
	public CurrencyResponseDTO getCurrency(
			@RequestParam(required = true, name = "access_key") String accessKey,
			@RequestParam(required = true, name = "from") String from,
			@RequestParam(required = true, name = "to") String to,
			@RequestParam(required = true, name = "amount") Double amount);
}
