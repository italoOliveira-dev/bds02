package com.devsuperior.bds02.services;

import com.devsuperior.bds02.exceptions.DatabaseIntegrityException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.web.dto.CityCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CityServiceIntegrationTests {


    final private CityService cityService;
    final private CityRepository cityRepository;

    @Autowired
    public CityServiceIntegrationTests(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    Long existingId;
    Long nonExistingId;
    Long independentId;
    Long dependentId;
    Long countTotalCity;

    @BeforeEach
    public void setUp() {
        existingId = 1L;
        nonExistingId = 50L;
        independentId = 5L;
        dependentId = 2L;
        countTotalCity = 11L;
    }

    @Test
    public void saveShouldPersist() {
        CityCreateOrUpdateDTO createDTO = new CityCreateOrUpdateDTO("Mossoró");

        CityDTO cityDTO = cityService.save(createDTO);

        Assertions.assertThat(cityDTO).isNotNull();
        Assertions.assertThat(cityDTO.name()).isEqualTo("Mossoró");
        Assertions.assertThat(++countTotalCity).isEqualTo(cityRepository.count());
    }

    @Test
    public void getByIdShouldReturnCityWhenExistsId() {
        City city = cityService.getById(existingId);

        Assertions.assertThat(city).isNotNull();
        Assertions.assertThat(city.getId()).isEqualTo(1);
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionWhenNonExistentId() {
        Assertions.assertThatThrownBy(() -> cityService.getById(nonExistingId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void findAllShouldReturnAllResourcesSortedByName() {
        List<CityDTO> cities = cityService.findAll();

        Assertions.assertThat(cities).isNotNull();
        Assertions.assertThat((long) cities.size()).isEqualTo(countTotalCity);
        Assertions.assertThat(cities.getFirst().name()).isEqualTo("Belo Horizonte");
        Assertions.assertThat(cities.get(1).name()).isEqualTo("Belém");
        Assertions.assertThat(cities.get(2).name()).isEqualTo("Brasília");
    }

    @Test
    public void updateShouldPersistWhenExistingId() {
        CityCreateOrUpdateDTO updateDTO = new CityCreateOrUpdateDTO("Novo Mossoró");
        CityDTO cityDTO = cityService.update(existingId, updateDTO);

        Assertions.assertThat(cityDTO).isNotNull();
        Assertions.assertThat(cityDTO.id()).isEqualTo(existingId);
        Assertions.assertThat(cityDTO.name()).isEqualTo("Novo Mossoró");
    }

    @Test
    public void updateShouldThrowEntityNotFoundExceptionWhenNonExistentId() {
        Assertions.assertThatThrownBy(
                () -> cityService.update(nonExistingId, new CityCreateOrUpdateDTO("Novo Mossoró"))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deleteShouldDeleteCityWhenIndependentId() {
        cityService.delete(independentId);
        Assertions.assertThat(--countTotalCity).isEqualTo(cityRepository.count());
    }

    @Test
    public void deleteShouldThrowEntityNotFoundExceptionWhenNonExistentId() {
        Assertions.assertThatThrownBy(() -> cityService.delete(nonExistingId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deleteShouldThrowDatabaseIntegrityExceptionWhenDependentId() {
        Assertions.assertThatThrownBy(() -> cityService.delete(dependentId)).isInstanceOf(DatabaseIntegrityException.class);
    }
}
