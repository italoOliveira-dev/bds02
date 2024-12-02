package com.devsuperior.bds02.web.dto;

import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;

import java.time.LocalDate;

public record EventCreateOrUpdateDTO(String name, LocalDate date, String url, Long cityId) {
    public Event toEntity(City city) {
        return new Event(name, date, url, city);
    }
}
