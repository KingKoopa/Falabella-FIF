package com.falabella.fif.bender.beermaster.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.dto.BoxPriceDTO;
import com.falabella.fif.bender.beermaster.entity.BeerItem;
import com.falabella.fif.bender.beermaster.exception.BeerErrorException;
import com.falabella.fif.bender.beermaster.exception.NotFoundException;
import com.falabella.fif.bender.beermaster.repository.BeerItemRepository;
import com.falabella.fif.bender.beermaster.service.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/beers")
public class BeerController {
	
	private final BeerService beerService;
    private final BeerItemRepository repository;
    
    @GetMapping()
    public Collection<BeerItem> allBeers() {
    	log.info("BeerController::allBeers()");
        return repository.findAll().stream()
                .collect(Collectors.toList());
    }
    
    @PostMapping()
    public ResponseEntity<BeerItemDTO> saveNewBeer(@RequestBody @NotNull BeerItemDTO beerItemDTO) throws Exception {
    	log.info("BeerController::saveNewBeer({})", beerItemDTO);
    	
    	BeerItemDTO beerItem = Optional.ofNullable(beerService.saveBeer(beerItemDTO))
    	.orElseThrow(() -> new BeerErrorException("El ID de la cerveza ya existe"));

    	return new ResponseEntity<>(beerItem, HttpStatus.CREATED);
    }
    
    @GetMapping("/{beerID}")
    public ResponseEntity<BeerItemDTO> findById(@PathVariable("beerID") @NotNull Long beerId) throws NotFoundException {
    	log.info("BeerController::findById({})", beerId);
    	BeerItem beerItem = repository.findById(beerId)
    			.orElseThrow(() -> new NotFoundException("El Id de la cerveza no existe"));
    	
    	//copiamos el objeto model a dto:
    	BeerItemDTO beerItemDTO = new BeerItemDTO();
    	BeanUtils.copyProperties(beerItem, beerItemDTO);
        return new ResponseEntity<>(beerItemDTO, HttpStatus.OK);
    }
    
    @GetMapping("/{beerID}/boxprice")
    public ResponseEntity<BoxPriceDTO> calculateBoxPriceById(@PathVariable("beerID") @NotNull Long beerId,
    		@RequestParam(name = "currency", required = false) String currency,
    		@RequestParam(name = "quantity", defaultValue = "6", required = false) Integer quantity) throws Exception
    {
    	log.info("BeerController::calculateBoxPriceById({})", beerId);
        return new ResponseEntity<>(beerService.calculateBoxPriceById(beerId, currency, quantity), HttpStatus.OK);
    }

}