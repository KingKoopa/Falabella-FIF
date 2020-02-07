package com.falabella.fif.bender.beermaster.bootstrapping;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.entity.BeerItem;
import com.falabella.fif.bender.beermaster.repository.BeerItemRepository;
@Component
public class BeerCommandLineRunner implements CommandLineRunner {
	
    private final BeerItemRepository repository;
    
    public BeerCommandLineRunner(BeerItemRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void run(String... strings) throws Exception {
    	
        Stream.of(BeerItemDTO.builder()
        		.id(1L)
        		.name("Hoegaarden")
        		.brewery("Golden")
        		.country("Bélgica")
        		.currency("CLP")
        		.price(1290.0)
        		.build(),
        		BeerItemDTO.builder()
        		.id(2L)
        		.name("Austral")
        		.brewery("Calafate")
        		.country("Chile")
        		.currency("CLP")
        		.price(1990.0)
        		.build()
        		).forEach(beerItem ->
            repository.save(new BeerItem(beerItem.getId(), beerItem.getName(), beerItem.getBrewery(), beerItem.getCountry(), beerItem.getPrice(), beerItem.getCurrency()))
        );
        repository.findAll().forEach(System.out::println);
    }
}
