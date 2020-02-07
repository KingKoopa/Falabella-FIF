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
public class BoxPriceDTO {

    @NotNull(message = "el atributo 'totalPrice' no puede ser nulo.")
    private Double totalPrice;
    
}
