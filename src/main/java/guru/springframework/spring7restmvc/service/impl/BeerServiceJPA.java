package guru.springframework.spring7restmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.spring7restmvc.mapper.BeerMapper;
import guru.springframework.spring7restmvc.model.dto.BeerDTO;
import guru.springframework.spring7restmvc.model.dto.BeerStyle;
import guru.springframework.spring7restmvc.model.entity.Beer;
import guru.springframework.spring7restmvc.repository.BeerRepository;
import guru.springframework.spring7restmvc.service.BeerService;


import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by jt, Spring Framework Guru.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = (pageNumber == null || pageNumber < 0) ? DEFAULT_PAGE : pageNumber -1; // guru wants page from 1 not 0
        int queryPageSize = (pageSize == null || pageSize < 1 || pageSize > 1000) ? DEFAULT_PAGE_SIZE : pageSize;

        // add sort on beer name ascending
        Sort sort = Sort.by("beerName").ascending();
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    Page<Beer> listBeersByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
    }
    Page<Beer> listBeersByNameAndStyle(String beerName, String beerStyle, PageRequest pageRequest) {
        BeerStyle style = BeerStyle.valueOf(beerStyle.toUpperCase());
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", style, pageRequest);
    }
    Page<Beer> listBeersByStyle(String beerStyle, PageRequest pageRequest) {
        BeerStyle style = BeerStyle.valueOf(beerStyle.toUpperCase());
        return beerRepository.findAllByBeerStyle(style, pageRequest);
    }


    /**
     * Retrieve a list of BeerDTOs filtered by optional name and/or style.
     *
     * Behavior:
     * - If both beerName (non-blank) and beerStyle (non-null) are provided, returns beers matching both name and style.
     * - If only beerStyle (non-null) is provided, returns beers matching that style.
     * - If only beerName (non-blank) is provided, returns beers matching that name.
     * - If neither filter is provided, returns all beers.
     *
     * @param beerName optional name filter; treated as absent when null or blank
     * @param beerStyle optional BeerStyle enum filter; when non-null, the enum's .name() is used for comparisons
     * @param showInventory if false, the quantityOnHand field in the returned BeerDTOs will be nullified
     * @param pageNumber optional page number for pagination 
     * @param pageSize optional page size for pagination 
     * @return list of BeerDTOs corresponding to the matching Beer entities
     *
     * Note: enumType.name() returns the exact identifier String of the enum constant as declared (for example "LAGER").
     * This is the literal enum constant name and differs from a localized or user-friendly label.
     */
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        Page<Beer> beerPage;

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle.name(), pageRequest);
        } else if (beerStyle != null && !StringUtils.hasText(beerName)) {
            beerPage = listBeersByStyle(beerStyle.name(), pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle == null)  {
            beerPage = listBeersByName(beerName, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        // null check to avoid NPE if showInventory is not provided, false if one wants to mask inventory
        if(showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null)); 

        //    return beerPage.stream()
        //            .map(beer -> {
        //                BeerDTO dto = beerMapper.beerToBeerDto(beer);
        //                dto.setQuantityOnHand(null);
        //                return dto;
        //            })
        //            .collect(Collectors.toList());
        }

        return beerPage.map(beerMapper::beerToBeerDto); // from Beer (entity) to BeerDTO to Page<BeerDTO>
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Eror, no ID provided to get BeerDTO");
        }
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        Beer beerEntity = beerMapper.beerDtoToBeer(beer);
        if (beerEntity == null) {
            throw new IllegalArgumentException("Failed to convert BeerDTO to Beer entity");
        }
        return beerMapper.beerToBeerDto(beerRepository.save(beerEntity));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        if (beerId == null) {
            throw new IllegalArgumentException("Eror, no ID provided to convert BeerDTO to Beer entity");
        }
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if (beerId == null) {
            throw new IllegalArgumentException("Eror, no ID provided to convert BeerDTO to Beer entity");
        }
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        if (beerId == null) {
            throw new IllegalArgumentException("Eror, no ID provided to convert BeerDTO to Beer entity");
        }
        
        var foundBeer = beerRepository.findById(beerId).get();
        if (foundBeer == null) {
            return Optional.empty();
        }
        else { 
            if (StringUtils.hasText(beer.getBeerName())) {
                foundBeer.setBeerName(beer.getBeerName());
            }   
            if (beer.getBeerStyle() != null){
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            return Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer)));
        }
    }
    
    @Override
    public Optional<BeerDTO> getBeerByUpc(String upc) {
        return Optional.ofNullable(beerRepository.findByUpc(upc))
                .map(beer -> beerMapper.beerToBeerDto(beer));    
    }
}
