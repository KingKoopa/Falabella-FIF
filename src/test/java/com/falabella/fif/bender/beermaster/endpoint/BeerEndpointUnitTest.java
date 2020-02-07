package com.falabella.fif.bender.beermaster.endpoint;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.falabella.fif.bender.beermaster.controller.BeerController;
import com.falabella.fif.bender.beermaster.dto.BeerItemDTO;
import com.falabella.fif.bender.beermaster.dto.BoxPriceDTO;
import com.falabella.fif.bender.beermaster.entity.BeerItem;
import com.falabella.fif.bender.beermaster.repository.BeerItemRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {BeerItemRepository.class})
public class BeerEndpointUnitTest {

	
	@Autowired
    MockMvc mockMvc;

    @MockBean
    private BeerController beerController;
    
    @Test
    void when_get_all_beers_is_ok() throws Exception {

        Mockito.when(beerController.allBeers()).thenReturn(Arrays.asList(BeerItem.builder()
											        		.id(1L)
											        		.name("Hoegaarden")
											        		.brewery("Golden")
											        		.country("Bélgica")
											        		.currency("CLP")
											        		.price(1290.0)
											                .build(),
											                BeerItem.builder()
											                .id(2L)
											        		.name("Austral")
											        		.brewery("Calafate")
											        		.country("Chile")
											        		.currency("CLP")
											        		.price(1990.0)
											                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/beers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
    
    @Test
    void when_get_all_beers_is_null() throws Exception {

        Mockito.when(beerController.allBeers()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/beers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").doesNotExist())
                .andDo(print());

    }
    
    @Test
    void when_find_beer_byId_is_ok() throws Exception {

    	BeerItemDTO beerItemDTO = BeerItemDTO.builder()
											  .id(1L)
									          .build();
    	
        Mockito.when(beerController.findById(1L)).thenReturn(new ResponseEntity<BeerItemDTO>(beerItemDTO, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/beers/{beerID}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
    
    @Test
    void when_find_beer_byId_is_empty() throws Exception {

    	BeerItemDTO beerItemDTO = BeerItemDTO.builder()
											  .id(3L)
									          .build();
    	
        Mockito.when(beerController.findById(3L)).thenReturn(new ResponseEntity<BeerItemDTO>(beerItemDTO, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/beers/{beerID}", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").doesNotExist())
                .andDo(print());

    }
    
    @Test
    void when_calculate_boxPriceById_is_ok() throws Exception {

    	BoxPriceDTO boxPriceDTO = BoxPriceDTO.builder()
											  .totalPrice(19.6596) //el valor en USD para 12 beers
									          .build();
    	
        Mockito.when(beerController.calculateBoxPriceById(1L, "USD", 12)).thenReturn(new ResponseEntity<BoxPriceDTO>(boxPriceDTO, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/beers/{beerID}/boxprice?currency=USD&quantity=12", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(boxPriceDTO.getTotalPrice())))
                .andDo(print());

    }
    
    @Test
    void when_calculate_boxPriceById_is_null() throws Exception {

        Mockito.when(beerController.calculateBoxPriceById(1L, "USD", 12)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/beers/{beerID}/boxprice?currency=USD&quantity=12", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").doesNotExist())
                .andDo(print());

    }
}
