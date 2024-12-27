package com.j_norrman.examensarbete.eventhandlerMarkedForRemoval.controller;

import com.j_norrman.examensarbete.eventhandlerMarkedForRemoval.model.EventDto;
import com.j_norrman.examensarbete.eventhandlerMarkedForRemoval.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Deprecated
@RestController
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/get-all-events")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        try {
            List<EventDto> events = eventService.findAllEvent();
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to fetch events", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-event")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        try {
            if (eventDto.getEventType() == null || eventDto.getMessage() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            EventDto savedEvent = eventService.saveEvent(eventDto);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Failed to save event", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
