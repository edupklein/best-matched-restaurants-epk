package org.restaurantsearch.util;

import org.restaurantsearch.service.search.RestaurantSearchCriteria;

public class RestaurantSearchValidator {

    public static void validateSearchCriteria(RestaurantSearchCriteria searchCriteria) {
        if (searchCriteria.getRating() != null &&
                (searchCriteria.getRating() < 1 || searchCriteria.getRating() > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        if (searchCriteria.getDistance() != null &&
                (searchCriteria.getDistance() < 1 || searchCriteria.getDistance() > 10)) {
            throw new IllegalArgumentException("Distance must be between 1 and 10 miles");
        }

        if (searchCriteria.getPrice() != null &&
                (searchCriteria.getPrice() < 10 || searchCriteria.getPrice() > 50)) {
            throw new IllegalArgumentException("Price must be between $10 and $50");
        }
    }
}
