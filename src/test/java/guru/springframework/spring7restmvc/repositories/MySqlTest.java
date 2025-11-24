package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import guru.springframework.spring7restmvc.entities.Beer;
import javax.sql.DataSource;


@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTest {

        @Autowired
        DataSource dataSource;

        @Autowired
        BeerRepository beerRepository;

        @Test
        void testListBeers() {
            List<Beer> listBeers = beerRepository.findAll();
            listBeers.forEach(beer -> {
                System.out.println(beer.getBeerName());
            });
            System.out.println("DataSource Class: " + dataSource.getClass().getName());
            System.out.println("Repository: " + beerRepository.getClass().getName());
            assertThat(listBeers.size()).isGreaterThan(0);
        }
}
