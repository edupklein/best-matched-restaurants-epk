package org.restaurantsearch;

import org.restaurantsearch.model.Cuisine;
import org.restaurantsearch.model.Restaurant;
import org.restaurantsearch.service.loader.CuisineCsvLoader;
import org.restaurantsearch.service.loader.RestaurantCsvLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Starting Restaurant Search CLI ===");


        CuisineCsvLoader cuisineCsvLoader = new CuisineCsvLoader();
        Map<String, Cuisine> cuisineMap = cuisineCsvLoader.loadCuisineMap();

        RestaurantCsvLoader restaurantCsvLoader = new RestaurantCsvLoader(cuisineMap);
        List<Restaurant> restaurants = restaurantCsvLoader.loadRestaurants();

        restaurants.forEach(System.out::println);

        // Search example
        /*
        var results = searchService.search("Mcd", 3, null, null, null);
        results.forEach(r ->
                System.out.printf("%s | Rating: %d | Distance: %.1f | Price: %.2f | Cuisine: %s%n",
                        r.getName(), r.getCustomerRating(), r.getDistance(), r.getPrice(), r.getCuisine().getName())
        );
        */

        System.out.println("=== CLI Execution Finished ===");
    }
}
