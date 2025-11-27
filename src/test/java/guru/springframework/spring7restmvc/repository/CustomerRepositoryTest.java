package guru.springframework.spring7restmvc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import guru.springframework.spring7restmvc.model.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("localmysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@org.junit.jupiter.api.Tag("mysql")
class CustomerRepositoryTest extends guru.springframework.spring7restmvc.test.AbstractMySqlIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
            .name("New Name")
            .build());

        assertThat(customer.getId()).isNotNull();

    }
}