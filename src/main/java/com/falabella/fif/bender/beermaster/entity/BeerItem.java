package com.falabella.fif.bender.beermaster.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class BeerItem {
	
	
    public BeerItem(Long id, @NonNull String name, @NonNull String brewery, @NonNull String country,
			@NonNull Double price, @NonNull String currency) {
		super();
		this.id = id;
		this.name = name;
		this.brewery = brewery;
		this.country = country;
		this.price = price;
		this.currency = currency;
	}
    
	@Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String brewery;
    @NonNull
    private String country;
    @NonNull
    private Double price;
    @NonNull
    private String currency;
}
