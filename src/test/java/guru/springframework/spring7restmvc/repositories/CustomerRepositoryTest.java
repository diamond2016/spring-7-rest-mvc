package guru.springframework.spring7restmvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import guru.springframework.spring7restmvc.model.entity.Customer;
import guru.springframework.spring7restmvc.repository.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

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