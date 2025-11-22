package guru.springframework.spring7restmvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created by jt, Spring Framework Guru.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
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
        var beerEntity = beerMapper.beerDtoToBeer(beer);
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
        var foundBeer = beerRepository.findById(beerId);
        
        if (foundBeer.isEmpty()) {
            return Optional.empty();
        }
        else { 
            var beerToUpdate = foundBeer.get();
            if (StringUtils.hasText(beer.getBeerName())) {
                beerToUpdate.setBeerName(beer.getBeerName());
            }   
            if (beer.getBeerStyle() != null){
                beerToUpdate.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                beerToUpdate.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                beerToUpdate.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
            }
            return Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate)));
        }
    }
}
