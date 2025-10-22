package org.restaurantsearch.service.search;

import org.restaurantsearch.dto.RestaurantResponse;
import org.restaurantsearch.model.Restaurant;
import org.restaurantsearch.util.RestaurantSearchValidator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RestaurantSearchService {

    private final List<Restaurant> restaurants;

    public RestaurantSearchService(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<RestaurantResponse> search(RestaurantSearchCriteria searchCriteria) {
        RestaurantSearchValidator.validateSearchCriteria(searchCriteria);

        return restaurants.stream()
                .filter(byName(searchCriteria.getName()))
                .filter(byRating(searchCriteria.getRating()))
                .filter(byDistance(searchCriteria.getDistance()))
                .filter(byPrice(searchCriteria.getPrice()))
                .filter(byCuisine(searchCriteria.getCuisine()))
                .sorted(bestMatchComparator())
                .limit(5)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private void validateParams(Double customerRating, Double distance, Double price) {
        if (customerRating != null && (customerRating < 1 || customerRating > 5))
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        if (distance != null && (distance < 1 || distance > 10))
            throw new IllegalArgumentException("Distance must be between 1 and 10 miles");
        if (price != null && (price < 10 || price > 50))
            throw new IllegalArgumentException("Price must be between $10 and $50");
    }

    private Predicate<Restaurant> byName(String name) {
        if(name == null || name.isBlank()) return r -> true;
        String lower = name.toLowerCase();
        return r -> r.getName().toLowerCase().contains(lower);
    }

    private Predicate<Restaurant> byRating(Double customerRating) {
        if(customerRating == null) return r -> true;
        return r -> r.getCustomerRating() >= customerRating;
    }

    private Predicate<Restaurant> byDistance(Double distance) {
        if(distance == null) return r -> true;
        return r -> r.getDistance() <= distance;
    }

    private Predicate<Restaurant> byPrice(Double price) {
        if(price == null) return r -> true;
        return r -> r.getPrice() <= price;
    }

    private Predicate<Restaurant> byCuisine(String cuisine) {
        if(cuisine == null || cuisine.isBlank()) return r -> true;
        String lower = cuisine.toLowerCase();
        return r -> r.getCuisine().getName().toLowerCase().contains(lower);
    }

    private Comparator<Restaurant> bestMatchComparator() {
        return Comparator
                .comparing(Restaurant::getDistance)
                .thenComparing(Comparator.comparing(Restaurant::getCustomerRating).reversed())
                .thenComparing(Restaurant::getPrice);
    }

    private RestaurantResponse toResponse(Restaurant r) {
        return new RestaurantResponse(
                r.getName(),
                r.getCustomerRating(),
                r.getDistance(),
                r.getPrice(),
                r.getCuisine().getName()
        );
    }
}
