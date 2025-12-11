package guru.springframework.spring7restmvc.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
import guru.springframework.spring7restmvc.model.dto.BeerStyle;
import guru.springframework.spring7restmvc.model.entity.Beer;
import guru.springframework.spring7restmvc.model.entity.Customer;
import guru.springframework.spring7restmvc.repository.BeerRepository;
import guru.springframework.spring7restmvc.repository.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;


import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional   // if errors in loading rollback
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356333")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
        List<Beer> listBeers = List.of(beer1, beer2, beer3);
        if (listBeers == null) {
            throw new IllegalArgumentException("Eror in creating beers from build()");
        }
        beerRepository.saveAll(listBeers);
        }

    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() > 10) {
            // implement CSV loading only one time
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCsvRecord> beerCSVRecordList = beerCsvService.convertCSV(file);
            beerCSVRecordList.forEach(record -> {
                BeerStyle beerStyle = switch (record.getStyle().toUpperCase()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pilsner" -> BeerStyle.PILSNER;
                    case "American Black Ale" -> BeerStyle.BLACK_ALE;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "WHEAT" -> BeerStyle.WHEAT;
                    case "American Pale Ale (APA)\"" -> BeerStyle.PALE_ALE;
                    case "American IPA" -> BeerStyle.IPA;
                    default -> BeerStyle.WHEAT;
                };
                Beer beer = Beer.builder()
                        .beerName(record.getBeer())
                        .beerStyle(beerStyle)
                        .upc(record.getRow().toString()) // dummy UPC
                        .price(new BigDecimal(10.00)) // dummy price
                        .quantityOnHand(Integer.valueOf(record.getCount())) // dummy quantity
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
                beerRepository.save(beer);
            }); 
        }
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Customer 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Customer 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Customer 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            List<Customer> listCustomers = List.of(customer1, customer2, customer3);
            if (listCustomers == null) {
                throw new IllegalArgumentException("Error in creating customers from build()");
            }
            customerRepository.saveAll(listCustomers);
        }
    }
}
