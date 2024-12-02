package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.services.CityService;
import com.devsuperior.bds02.services.EventService;
import com.devsuperior.bds02.web.dto.CityCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService, EventService eventService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityDTO> save(@RequestBody CityCreateOrUpdateDTO createDto, UriComponentsBuilder uriBuilder) {
        CityDTO city = cityService.save(createDto);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(city.id()).toUri();
        return ResponseEntity.created(uri).body(city);
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAll() {
        List<CityDTO> cities = cityService.findAll();
        return ResponseEntity.ok().body(cities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityCreateOrUpdateDTO updateDTO) {
        CityDTO cityDTO = cityService.update(id, updateDTO);
        return ResponseEntity.ok().body(cityDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
