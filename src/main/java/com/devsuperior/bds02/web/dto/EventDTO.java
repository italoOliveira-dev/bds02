package com.devsuperior.bds02.web.dto;

import com.devsuperior.bds02.entities.Event;

import java.time.LocalDate;

public record EventDTO(Long id, String name, LocalDate date, String url, Long cityId) {
    public static EventDTO toDTO(Event event) {
        return new EventDTO(event.getId(), event.getName(), event.getDate(), event.getUrl(), event.getCity().getId());
    }
}
