package com.devsuperior.bds02.services;

import com.devsuperior.bds02.exceptions.DatabaseIntegrityException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.web.dto.CityCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CityService(CityRepository repository, EventRepository eventRepository) {
        this.cityRepository = repository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public CityDTO save(CityCreateOrUpdateDTO createDTO) {
        City citySave = cityRepository.save(createDTO.toEntity());
        return CityDTO.toDTO(citySave);
    }

    public City getById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found"));
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        return cityRepository.findAll(Sort.by("name")).stream().map(CityDTO::toDTO).toList();
    }

    @Transactional
    public CityDTO update(Long id, CityCreateOrUpdateDTO updateDTO) {
        City city = getById(id);
        city.setName(updateDTO.name());
        City citySave = cityRepository.save(city);
        return CityDTO.toDTO(citySave);
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        long count = eventRepository.countCityById(id);
        if (count > 0) {
            throw new DatabaseIntegrityException("Não é possível excluir a cidade. Existem eventos associados a ela.");
        }
        cityRepository.deleteById(id);
    }
}
