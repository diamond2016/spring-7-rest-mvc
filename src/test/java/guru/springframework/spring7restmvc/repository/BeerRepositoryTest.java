package guru.springframework.spring7restmvc.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import guru.springframework.spring7restmvc.model.dto.BeerStyle;
import guru.springframework.spring7restmvc.model.entity.Beer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("localmysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@org.junit.jupiter.api.Tag("mysql")
class BeerRepositoryTest extends guru.springframework.spring7restmvc.test.AbstractMySqlIntegrationTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = Beer.builder()
                    .beerName("My Beer 0123345678901233456789012334567890123345678901233456789012334567890123345678901233456789")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("234234234234")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(100)
                    .build();
            beerRepository.save(savedBeer);
            assertThat(savedBeer).isNotNull();
            assertThat(savedBeer.getId()).isNotNull();

            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("234234234234")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(100)
                .build();
        beerRepository.save(savedBeer);
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        beerRepository.flush();


    }
}