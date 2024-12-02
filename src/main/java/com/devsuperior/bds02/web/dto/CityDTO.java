package com.devsuperior.bds02.web.dto;

import com.devsuperior.bds02.entities.City;

public record CityDTO(Long id, String name) {
	public static CityDTO toDTO(City city) {
		return new CityDTO(city.getId(), city.getName());
	}
}
