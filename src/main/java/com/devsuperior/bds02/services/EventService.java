package com.devsuperior.bds02.services;

import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.web.dto.EventCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CityService cityService;

    @Autowired
    public EventService(EventRepository eventRepository, CityService cityService) {
        this.eventRepository = eventRepository;
        this.cityService = cityService;
    }

    @Transactional
    public EventDTO save(EventCreateOrUpdateDTO createDTO) {
        City city = cityService.getById(createDTO.cityId());
        Event eventSave = eventRepository.save(createDTO.toEntity(city));

        return EventDTO.toDTO(eventSave);
    }

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventDTO::toDTO);
    }

    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    @Transactional
    public EventDTO update(Long id, EventCreateOrUpdateDTO updateDTO) {
        Event event = getById(id);
        updateFields(event, updateDTO);
        eventRepository.save(event);
        return EventDTO.toDTO(event);
    }

    private void updateFields(Event event, EventCreateOrUpdateDTO updateDTO) {
        event.setName(updateDTO.name());
        event.setDate(updateDTO.date());
        event.setUrl(updateDTO.url());
        event.setCity(cityService.getById(updateDTO.cityId()));
    }

    public void delete(Long id) {
        getById(id);
        eventRepository.deleteById(id);
    }
}
