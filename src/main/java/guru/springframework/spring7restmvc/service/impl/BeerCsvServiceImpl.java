package guru.springframework.spring7restmvc.service.impl;
import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
import guru.springframework.spring7restmvc.service.BeerCsvService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class BeerCsvServiceImpl implements BeerCsvService   {

    @Override
    public List<BeerCsvRecord> convertCSV(File file) throws FileNotFoundException {
        try (FileReader fileReader = new FileReader(file)) {
            List<BeerCsvRecord> beerCsvRecordList = new CsvToBeanBuilder<BeerCsvRecord>(fileReader)
                .withType(BeerCsvRecord.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
            return beerCsvRecordList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert CSV to BeerCsvRecord list", e);
        }       
    }
}