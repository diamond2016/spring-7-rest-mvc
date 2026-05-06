package guru.springframework.spring7restmvc.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
import guru.springframework.spring7restmvc.repository.BeerRepository;
import guru.springframework.spring7restmvc.repository.CategoryRepository;
import guru.springframework.spring7restmvc.repository.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("localh2")
// the test is for h2 where we do not load csv record (for now)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @MockitoBean
    BeerCsvService beerCsvService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DataSource dataSource;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        // run Flyway migrations against the test DataSource so the H2 DB is populated
        // restrict locations to the H2-specific migrations to avoid duplicate versions
        Flyway.configure().locations("classpath:db/migration/h2").dataSource(dataSource).load().migrate();
        try {
        Mockito.when(beerCsvService.convertCSV(Mockito.any(File.class))).thenReturn(new ArrayList<BeerCsvRecord>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService, categoryRepository);
    }

    @Test
    void Testrun() throws Exception {
         bootstrapData.run((String[]) null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(categoryRepository.count()).isEqualTo(1);
    }
}

