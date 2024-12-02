package com.devsuperior.bds02.services;

import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.web.dto.EventCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.EventDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class EventServiceIntegrationTests {

    private final EventService eventService;

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceIntegrationTests(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    Long existingId;
    Long nonExistingId;
    Long countTotalEvents;

    @BeforeEach
    public void setUp() {
        existingId = 1L;
        nonExistingId = 50L;
        countTotalEvents = 4L;
    }

    @Test
    public void saveShouldPersistEvent() {
        EventCreateOrUpdateDTO createDTO =
                new EventCreateOrUpdateDTO(
                        "Congresso Linux", LocalDate.of(2024, 12, 2), "https://congressolinux.com.br", 2L
                );
        EventDTO eventSave = eventService.save(createDTO);

        Assertions.assertThat(eventSave).isNotNull();
        Assertions.assertThat(countTotalEvents + 1).isEqualTo(eventRepository.count());
        Assertions.assertThat(eventSave.id()).isNotNull();
        Assertions.assertThat(eventSave.name()).isEqualTo("Congresso Linux");
        Assertions.assertThat(eventSave.date()).isEqualTo(LocalDate.of(2024, 12, 2));
    }

    @Test
    public void findAllShouldReturnAllEventsPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EventDTO> eventPaged = eventService.findAll(pageable);

        Assertions.assertThat(eventPaged).isNotNull();
        Assertions.assertThat(eventPaged.getTotalElements()).isEqualTo(countTotalEvents);
        Assertions.assertThat(eventPaged.getTotalPages()).isEqualTo(1);
    }

    @Test
    public void getByIdShouldReturnEventWhenExistingId() {
        Event event = eventService.getById(existingId);

        Assertions.assertThat(event).isNotNull();
        Assertions.assertThat(event.getId()).isEqualTo(existingId);
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionWhenNonExistingId() {
        Assertions.assertThatThrownBy(() -> eventService.getById(nonExistingId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void updateShouldUpdateEventWhenExistingId() {
        EventCreateOrUpdateDTO updateDTO =
                new EventCreateOrUpdateDTO(
                        "Expo XP", LocalDate.of(2024, 10, 21), "https://expoxp.com.br", 7L
                );
        EventDTO eventDTO = eventService.update(existingId, updateDTO);

        Assertions.assertThat(eventDTO).isNotNull();
        Assertions.assertThat(eventDTO.id()).isEqualTo(existingId);
        Assertions.assertThat(eventDTO.name()).isEqualTo("Expo XP");
        Assertions.assertThat(eventDTO.date()).isEqualTo(LocalDate.of(2024, 10, 21));
    }

    @Test
    public void updateShouldThrowEntityNotFoundExceptionWhenNonExistingId() {
        EventCreateOrUpdateDTO updateDTO =
                new EventCreateOrUpdateDTO(
                        "Expo XP", LocalDate.of(2024, 10, 21), "https://expoxp.com.br", 7L
                );
        Assertions.assertThatThrownBy(() -> eventService.update(nonExistingId, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deleteShouldDeleteEventWhenExistingId() {
        eventService.delete(existingId);

        Assertions.assertThat(eventRepository.existsById(existingId)).isFalse();
        Assertions.assertThat(countTotalEvents - 1).isEqualTo(eventRepository.count());
    }

    @Test
    public void deleteShouldThrowEntityNotFoundExceptionWhenNonExistingId() {
        Assertions.assertThatThrownBy(() -> eventService.delete(nonExistingId)).isInstanceOf(EntityNotFoundException.class);
    }
}
