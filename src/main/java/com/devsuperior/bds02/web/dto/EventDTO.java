package com.devsuperior.bds02.web.dto;

import java.time.LocalDate;

public record EventDTO(Long id, String name, LocalDate date, String url, Long cityId) {
}
