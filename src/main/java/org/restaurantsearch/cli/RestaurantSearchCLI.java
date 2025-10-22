package org.restaurantsearch.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantsearch.dto.RestaurantResponse;
import org.restaurantsearch.service.search.RestaurantSearchCriteria;
import org.restaurantsearch.service.search.RestaurantSearchService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class RestaurantSearchCLI {

    private final RestaurantSearchService searchService;
    private final Scanner scanner = new Scanner(System.in);

    public RestaurantSearchCLI(RestaurantSearchService searchService) {
        this.searchService = searchService;
    }

    public void start() {
        // Create a loop to allow the user to run multiple searches
        boolean continueRunning = true;
        while (continueRunning) {
            // Get all search criteria from the user (ENTER to skip any)
            System.out.println("\nType the criteria of the search (press ENTER to skip):");
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Rating: ");
            Double rating = parseDouble(scanner.nextLine());

            System.out.print("Distance: ");
            Double distance = parseDouble(scanner.nextLine());

            System.out.print("Price: ");
            Double price = parseDouble(scanner.nextLine());

            System.out.print("Cuisine: ");
            String cuisine = scanner.nextLine();

            // Generate input object (search criteria)
            RestaurantSearchCriteria criteria = new RestaurantSearchCriteria();
            criteria.setName(name.isBlank() ? null : name);
            criteria.setRating(rating);
            criteria.setDistance(distance);
            criteria.setPrice(price);
            criteria.setCuisine(cuisine.isBlank() ? null : cuisine);

            // Fetch all results from search service
            List<RestaurantResponse> results = new ArrayList<>();
            try{
                results = searchService.search(criteria);
            }catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
                continueRunning = continueRunning();
                if(continueRunning) {
                    continue;
                }else{
                    return;
                }
            }

            // Show to the user the results formatted as JSON
            if(!results.isEmpty()) {
                System.out.println("\nResults: ");
            }else{
                System.out.println("\nNo results found.");
            }
            try {
                String json = new ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(results);
                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Asks if user wants to make another search
            continueRunning = continueRunning();
        }
    }

    private Double parseDouble(String input) {
        try { return Double.parseDouble(input); } catch(Exception e) { return null; }
    }

    private boolean continueRunning() {
        System.out.print("\nDo you wish to make another search? (y/n): ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }
}
