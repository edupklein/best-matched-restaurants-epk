package org.restaurantsearch.model;

import com.opencsv.bean.CsvBindByName;

public class Cuisine {
    @CsvBindByName(column = "id")
    String id;
    @CsvBindByName(column = "name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
