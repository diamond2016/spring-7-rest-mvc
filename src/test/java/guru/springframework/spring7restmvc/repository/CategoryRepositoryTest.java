package guru.springframework.spring7restmvc.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import guru.springframework.spring7restmvc.bootstrap.BootstrapData;
import guru.springframework.spring7restmvc.model.entity.Beer;
import guru.springframework.spring7restmvc.model.entity.Category;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;
import jakarta.transaction.Transactional;


@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class}) // includes BootstrapData to load initial data (csv)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BeerRepository beerRepository;
    Beer testBeer;

    @BeforeEach
    void setUp() throws Exception {
        testBeer = beerRepository.findAll().get(0);
    }


// test add category to a beer
    @Transactional
    @Test
    void testAddCategory() {
        try {
        Category savedCat = categoryRepository.save(Category.builder()
                .description("Ales")
                .version(1)
                .build());

        testBeer.addCategory(savedCat);
        Beer saveBeer = beerRepository.save(testBeer);

        System.out.println(saveBeer.getBeerName());
        assertTrue(testBeer.getCategories().stream()
            .anyMatch(category -> category.getId().equals(savedCat.getId())),
            "Saved category should be present in testBeer's categories");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 

// test remove category from a beer
    @Transactional
    @Test
    void testRemoveCategory() {
        try {
        Category savedCat = categoryRepository.save(Category.builder()
                .description("Ales")
                .version(1)
                .build());

        testBeer.addCategory(savedCat);
        Beer saveBeer = beerRepository.save(testBeer);
        
        // now remove
        testBeer.removeCategory(savedCat);
        beerRepository.save(testBeer);

        assertTrue(testBeer.getCategories().stream()
            .noneMatch(category -> category.getId().equals(savedCat.getId())),
            "Saved category should not be present in testBeer's categories");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 

    
}