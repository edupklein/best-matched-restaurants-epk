package org.restaurantsearch.service.loader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.restaurantsearch.model.Cuisine;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CuisineCsvLoader {

    public List<Cuisine> loadCuisines() {
        InputStream is = getClass().getResourceAsStream("/cuisines.csv");
        return new CsvToBeanBuilder<Cuisine>(new InputStreamReader(is))
                .withType(Cuisine.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
    }

    public Map<String, Cuisine> loadCuisineMap() {
        return loadCuisines().stream()
                .collect(Collectors.toMap(Cuisine::getId, Function.identity()));
    }
}
