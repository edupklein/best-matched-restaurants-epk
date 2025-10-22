package org.restaurantsearch.service.loader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.restaurantsearch.model.Cuisine;
import org.restaurantsearch.model.Restaurant;
import org.restaurantsearch.service.converter.RestaurantConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantCsvLoader {

    private final RestaurantConverter converter;

    public RestaurantCsvLoader(Map<String, Cuisine> cuisineMap) {
        this.converter = new RestaurantConverter(cuisineMap);
    }

    public List<Restaurant> loadRestaurants() {
        List<Restaurant> rawRestaurants = new ArrayList<>();

        try(InputStream is = getClass().getResourceAsStream("/restaurants.csv")) {
            rawRestaurants = new CsvToBeanBuilder<Restaurant>(new InputStreamReader(is))
                    .withType(Restaurant.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rawRestaurants.stream()
                .map(converter::convert)
                .toList();
    }
}
