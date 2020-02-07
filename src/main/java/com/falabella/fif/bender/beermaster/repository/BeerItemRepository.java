package com.falabella.fif.bender.beermaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.falabella.fif.bender.beermaster.entity.BeerItem;

@RepositoryRestResource
public interface BeerItemRepository extends JpaRepository<BeerItem, Long>{

}
