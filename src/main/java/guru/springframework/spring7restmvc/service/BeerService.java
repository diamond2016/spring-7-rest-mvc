package guru.springframework.spring7restmvc.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import guru.springframework.spring7restmvc.model.dto.BeerDTO;
import guru.springframework.spring7restmvc.model.dto.BeerStyle;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerService {

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> getBeerByUpc(String upc);
}
