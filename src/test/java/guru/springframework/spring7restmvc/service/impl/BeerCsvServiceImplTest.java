package guru.springframework.spring7restmvc.service.impl;

import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
import guru.springframework.spring7restmvc.service.BeerCsvService;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;


class BeerCsvServiceImplTest { 

    BeerCsvService beerCsvServiceImpl = new BeerCsvServiceImpl();

    @Test
    void testConvertCSV() {

        try {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            assertThat(file).isNotNull();
            List<BeerCsvRecord> beerCSVRecordList = beerCsvServiceImpl.convertCSV(file);
            assertThat(beerCSVRecordList.size()).isGreaterThan(10);
            System.out.println("beerCSVRecordList.size(): " + beerCSVRecordList.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }   

}

