package org.restaurantsearch.controller;

import org.restaurantsearch.dto.RestaurantResponse;
import org.restaurantsearch.service.search.RestaurantSearchCriteria;
import org.restaurantsearch.service.search.RestaurantSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantSearchController {

    private final RestaurantSearchService searchService;

    public RestaurantSearchController(RestaurantSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<RestaurantResponse> search(@RequestBody RestaurantSearchCriteria criteria) {
        return searchService.search(criteria);
    }
}
