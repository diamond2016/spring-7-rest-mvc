package guru.springframework.spring7restmvc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


import guru.springframework.spring7restmvc.model.dto.BeerCsvRecord;
/**
 * Created by jt, Spring Framework Guru.
 */

public interface BeerCsvService {
    List<BeerCsvRecord> convertCSV(File file) throws FileNotFoundException;
}
