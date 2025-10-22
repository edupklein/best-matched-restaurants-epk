package org.restaurantsearch.dto;

public class RestaurantResponse {
    private String name;
    private Double customerRating;
    private Double distance;
    private Double price;
    private String cuisine;

    public RestaurantResponse(String name, Double customerRating, Double distance, Double price, String cuisine) {
        this.name = name;
        this.customerRating = customerRating;
        this.distance = distance;
        this.price = price;
        this.cuisine = cuisine;
    }

    // Getters (Spring Boot / Jackson needs those to serialize into an object)
    public String getName() { return name; }
    public Double getCustomerRating() { return customerRating; }
    public Double getDistance() { return distance; }
    public Double getPrice() { return price; }
    public String getCuisine() { return cuisine; }
}