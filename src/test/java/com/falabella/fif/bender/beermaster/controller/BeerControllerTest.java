package com.falabella.fif.bender.beermaster.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.repository.BeerItemRepository;
import com.falabella.fif.bender.beermaster.service.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {BeerItemRepository.class})
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BeerService beerService;
    
    @Test
    void when_create_beer_is_ok() throws Exception {

    	BeerItemDTO beerItemDTO = BeerItemDTO.builder()
    			.id(3L)
    			.brewery("Torobayo")
    			.name("Kunstmann")
    			.country("Chile")
    			.currency("CLP")
    			.price(2000.0)
                .build();

        Mockito.when(beerService.saveBeer(any())).thenReturn(beerItemDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/beers")
                .content(objectMapper.writeValueAsString(beerItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    void saveBeer_test_is_404() throws Exception {
        //probaremos guardar una cerveza con id existente
    	BeerItemDTO beerItemDTO = BeerItemDTO.builder()
    			.id(2L) // ya existe el id 2
                .brewery("Bock")
                .name("Kunstmann")
                .country("Chile")
                .currency("EUR")
                .price(2500.0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/beers")
                .content(objectMapper.writeValueAsString(beerItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    void when_create_beer_is_exception() throws Exception {
        Mockito.when(beerService.saveBeer(any())).thenReturn(null);

      //probaremos guardar una cerveza con id existente
    	BeerItemDTO beerItemDTO = BeerItemDTO.builder()
    			.id(3L) 
                .brewery("Lager")
                .name("Cristal")
                .country("Chile")
                .currency("CLP")
                .price(1000.0)
                .build();
    	

    	Throwable thrown =
                assertThrows(Throwable.class,
                   () -> mockMvc.perform(MockMvcRequestBuilders.post("/beers")
           		        .content(objectMapper.writeValueAsString(beerItemDTO))
        		        .contentType(MediaType.APPLICATION_JSON)),
                   "Esperando que /beers lanze un excepcion");

        assertTrue(thrown.getMessage().length() > 0 );
        

    }
}
