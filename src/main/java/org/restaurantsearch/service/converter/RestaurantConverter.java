package org.restaurantsearch.service.converter;

import org.restaurantsearch.model.Cuisine;
import org.restaurantsearch.model.Restaurant;

import java.util.Map;

public class RestaurantConverter {

    private final Map<String, Cuisine> cuisineMap;

    public RestaurantConverter(Map<String, Cuisine> cuisineMap) {
        this.cuisineMap = cuisineMap;
    }

    public Restaurant convert(Restaurant restaurant) {
        Cuisine cuisine = cuisineMap.get(restaurant.getCuisineId());
        restaurant.setCuisine(cuisine);
        return restaurant;
    }
}
