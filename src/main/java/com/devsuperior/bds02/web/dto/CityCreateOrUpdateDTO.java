package com.devsuperior.bds02.web.dto;

import com.devsuperior.bds02.entities.City;

public record CityCreateOrUpdateDTO(String name) {
    public City toEntity() {
        return new City(name);
    }
}
