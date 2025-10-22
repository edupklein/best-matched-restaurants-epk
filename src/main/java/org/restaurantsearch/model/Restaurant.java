package org.restaurantsearch.model;

import com.opencsv.bean.CsvBindByName;

public class Restaurant {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "customer_rating")
    private Double customerRating;
    @CsvBindByName(column = "distance")
    private Double distance;
    @CsvBindByName(column = "price")
    private Double price;
    @CsvBindByName(column = "cuisine_id")
    private String cuisineId;

    private Cuisine cuisine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }
}