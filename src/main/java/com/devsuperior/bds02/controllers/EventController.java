package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.services.EventService;
import com.devsuperior.bds02.web.dto.EventCreateOrUpdateDTO;
import com.devsuperior.bds02.web.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventCreateOrUpdateDTO updateDTO) {
        EventDTO eventDTO = eventService.update(id, updateDTO);
        return ResponseEntity.ok().body(eventDTO);
    }
}
