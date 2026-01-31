package guru.springframework.spring7restmvc.repository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.spring7restmvc.bootstrap.BootstrapData;
import guru.springframework.spring7restmvc.model.entity.Beer;
import guru.springframework.spring7restmvc.model.entity.BeerOrder;
import guru.springframework.spring7restmvc.model.entity.Customer;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Basic repository tests for BeerOrderRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("mysql")
@Import({BootstrapData.class, BeerCsvServiceImpl.class}) // includes BootstrapData to load initial data (csv)
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;
    
    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        // BootstrapData will run automatically due to @Import annotation
        testCustomer = customerRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(testCustomer, "Test Customer should be present after bootstrap");
        testBeer = beerRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(testBeer, "Test Beer should be present after bootstrap"); 
    }
    
    @Test
    void repositoryIsNotNull() {
        assertNotNull(beerOrderRepository, "BeerOrderRepository should be injected");
        assertNotNull(customerRepository, "CustomerRepository should be injected");
        assertNotNull(beerRepository, "BeerRepository should be injected");
        assertTrue(beerOrderRepository.count() >= 0, "BeerOrderRepository should be operational");
        assertTrue(customerRepository.count() >= 0, "CustomerRepository should be operational");
        assertTrue(beerRepository.count() >= 0, "BeerRepository should be operational");
    }

    @Test
    void saveIncreasesCountAndFindByIdReturnsEntity() {
        long before = beerOrderRepository.count();

        BeerOrder order = new BeerOrder();
        order.setCustomer(testCustomer);
        System.out.println("Test BeerOrderRepository, customer name: " + testCustomer.getName());
        System.out.println("Test BeerOrderRepository, beer name: " + testBeer.getBeerName());
        BeerOrder saved = beerOrderRepository.save(order);

        assertNotNull(saved, "Saved entity should not be null");
        assertNotNull(saved.getId(), "Saved entity should have an id");
        assertEquals(before + 1, beerOrderRepository.count(), "Count should increase after save");

        Optional<BeerOrder> fetched = beerOrderRepository.findById(saved.getId());
        assertTrue(fetched.isPresent(), "Saved entity should be retrievable by id");
        assertEquals(saved.getId(), fetched.get().getId(), "Retrieved entity id should match saved id");
    }

    @Test
    void testBeerOrders() {
        BeerOrder order = BeerOrder.builder()
            .customerRef("ref of Customer order")
            .customer(testCustomer)
            .build();

        // with flush to ensure it hits the DB and popolates also the inverse relation customer->beerOrders
        // BeerOrder saved = beerOrderRepository.saveAndFlush(order);
        // we can use save and not saveAndFlush having modified entity BeerOrder to manage both sides of the relationship
        BeerOrder saved = beerOrderRepository.save(order);
        // from here is important to use saved not order because this is object with relations populated
        assertNotNull(saved, "Saved BeerOrder should not be null");
        assertNotNull(saved.getId(), "Saved BeerOrder should have an id");
        System.out.println("Saved BeerOrder ID: " + saved.getId());
        System.out.println("Associated Customer Name: " + saved.getCustomer().getName());
        assertEquals(testCustomer.getId(), saved.getCustomer().getId(), "Associated Customer ID should match testCustomer ID");
        // Now check that the customer's beerOrders set includes this order
        Customer fetchedCustomer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertNotNull(fetchedCustomer, "Fetched Customer should not be null");
        assertNotNull(fetchedCustomer.getBeerOrders(), "Fetched Customer's beerOrders should not be null");
        assertTrue(fetchedCustomer.getBeerOrders().stream()
            .anyMatch(bo -> bo.getId().equals(saved.getId())), "Customer's beerOrders should include the saved BeerOrder");
    }
}