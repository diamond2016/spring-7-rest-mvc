package guru.springframework.spring7restmvc.mapper;

import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.model.dto.BeerDTO;
import guru.springframework.spring7restmvc.model.entity.Beer;

/**
 * Created by jt, Spring Framework Guru.
 */
@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);

}
