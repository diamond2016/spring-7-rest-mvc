package guru.springframework.spring7restmvc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import guru.springframework.spring7restmvc.model.entity.Beer;
import guru.springframework.spring7restmvc.repository.BeerRepository;


@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:8.0.26");

    //@Autowired
    //DataSource dataSource;

    @Autowired
    BeerRepository beerRepository;
        
    // commented out due to use of ServiceConnection
    //@DynamicPropertySource  
    //static void setDatasourceProperties(DynamicPropertyRegistry registry) {
    //    registry.add("spring.datasource.url",
    //            () -> mySqlContainer.getJdbcUrl());
    //    registry.add("spring.datasource.username", () -> mySqlContainer.getUsername());
    //    registry.add("spring.datasource.password", () -> mySqlContainer.getPassword());
    //}
        
    @Test
    void testListBeers() {
        List<Beer> listBeers = beerRepository.findAll();
        listBeers.forEach(beer -> {
            System.out.println(beer.getBeerName());
        });
        //System.out.println("DataSource Class: " + dataSource.getClass().getName());
        System.out.println("Repository: " + beerRepository.getClass().getName());
        assertThat(listBeers.size()).isGreaterThan(0);
    }
}
