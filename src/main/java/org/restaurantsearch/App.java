package org.restaurantsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantsearch.cli.RestaurantSearchCLI;
import org.restaurantsearch.dto.RestaurantResponse;
import org.restaurantsearch.model.Cuisine;
import org.restaurantsearch.model.Restaurant;
import org.restaurantsearch.service.search.RestaurantSearchCriteria;
import org.restaurantsearch.service.search.RestaurantSearchService;
import org.restaurantsearch.service.loader.CuisineCsvLoader;
import org.restaurantsearch.service.loader.RestaurantCsvLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final RestaurantSearchCLI cli;

    public App(RestaurantSearchCLI cli) {
        this.cli = cli;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        cli.start();
    }
}
