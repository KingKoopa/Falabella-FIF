package com.falabella.fif.bender.beermaster.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerItemDTO {

    @NotNull(message = "el atributo 'id' no puede ser nulo.")
    private Long id;
    @NotNull(message = "el atributo 'name' no puede ser nulo.")
    private String name;
    @NotNull(message = "el atributo 'brewery' no puede ser nulo.")
    private String brewery;
    @NotNull(message = "el atributo 'country' no puede ser nulo.")
    private String country;
    @NotNull(message ="el atributo 'price' no puede ser nulo.")
    private Double price;
    @NotNull(message = "el atributo 'currency' no puede ser nulo.")
    private String currency;
}
