package guru.springframework.spring7restmvc.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
import guru.springframework.spring7restmvc.repository.BeerRepository;
import guru.springframework.spring7restmvc.repository.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("localh2")

class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @MockitoBean
    BeerCsvService beerCsvService;
    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        try {
        Mockito.when(beerCsvService.convertCSV(Mockito.any(File.class))).thenReturn(new ArrayList<BeerCsvRecord>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}

