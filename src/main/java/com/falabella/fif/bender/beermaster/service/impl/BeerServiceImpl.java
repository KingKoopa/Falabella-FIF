package com.falabella.fif.bender.beermaster.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.dto.BoxPriceDTO;
import com.falabella.fif.bender.beermaster.entity.BeerItem;
import com.falabella.fif.bender.beermaster.exception.BeerException;
import com.falabella.fif.bender.beermaster.exception.NotFoundException;
import com.falabella.fif.bender.beermaster.feign.client.response.CurrencyResponseDTO;
import com.falabella.fif.bender.beermaster.repository.BeerItemRepository;
import com.falabella.fif.bender.beermaster.service.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerServiceImpl implements BeerService {

	private final BeerItemRepository beerItemRepository;
		
	@Value("${currencylayer.accesskey}")
	private String accessKey;
	 
	@Override
	public BeerItemDTO saveBeer(BeerItemDTO beerItemDTO) throws Exception {
		log.info("Creación de una nueva Beer: ({})", beerItemDTO);	
		
		Optional<BeerItem> beerItemOp = beerItemRepository.findById(beerItemDTO.getId());
		
		//Lanzamos excepcion si ya existe la beer:
		beerItemOp
		.filter(item -> item.getId().compareTo(beerItemDTO.getId()) == 0)
		.ifPresent(s -> {
			try {
				throw new BeerException("Ya existe una beer con el mismo id");
			} catch (BeerException e) {
				e.printStackTrace();
			}
		});
		
		if(beerItemOp.isPresent()) {
			return null;
		} 

		//copiamos los objetos
		BeerItem newBeerItem = new BeerItem();
		BeanUtils.copyProperties(beerItemDTO, newBeerItem);
		beerItemRepository.save(newBeerItem);
		
		return beerItemDTO;
	}

	@Override
	public BoxPriceDTO calculateBoxPriceById(Long beerId, String toCurrency, Integer quantity) throws Exception {
		
		CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO();
		
		BeerItem beerItem = beerItemRepository.findById(beerId).orElseThrow(() -> new NotFoundException("El Id de la cerveza no existe."));
		
		//Si la cerveza existe hacemos la conversión de currrencies:
		Optional.of(beerItem).ifPresent(s -> {
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl  = 
					"http://api.currencylayer.com/convert" + "?access_key=" + accessKey + "&from=" + s.getCurrency() + "&to=" + toCurrency + "&amount=" + s.getPrice();
			HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3");
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			CurrencyResponseDTO responseFromCurrencyApi = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, CurrencyResponseDTO.class).getBody();
			//copiamos el objeto de response:
			BeanUtils.copyProperties(responseFromCurrencyApi, currencyResponseDTO);
		});
		
		//calcular el resultado obtenido de la respuesta, y multiplicarlo por la quantity:
		return Optional.of(currencyResponseDTO)
		.map(x -> x.getResult() * quantity)
		.map(y -> BoxPriceDTO.builder().totalPrice(y).build())
		.orElseThrow(() -> new BeerException("No fue posible calcular el precio final de las cajas de cervezas."));
		
		
	}

	
}
