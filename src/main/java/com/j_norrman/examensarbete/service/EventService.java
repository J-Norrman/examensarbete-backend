package com.j_norrman.examensarbete.service;

import com.j_norrman.examensarbete.model.Event;
import com.j_norrman.examensarbete.model.EventDto;
import com.j_norrman.examensarbete.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventDto> findAllEvent() {
        try {
            List<Event> events = eventRepository.findAll();
            return events.stream()
                    .map(event -> new EventDto(
                            event.getId(),
                            event.getEventType(),
                            event.getMessage(),
                            event.getDetails(),
                            event.getTimestamp()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch events", e);
        }
    }
    public EventDto saveEvent(EventDto eventDto) {
        Event event = new Event();
        event.setEventType(eventDto.getEventType());
        event.setMessage(eventDto.getMessage());
        event.setDetails(eventDto.getDetails());

        event.setTimestamp(eventDto.getTimestamp() != null
                ? eventDto.getTimestamp()
                : LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        return new EventDto(
                savedEvent.getId(),
                savedEvent.getEventType(),
                savedEvent.getMessage(),
                savedEvent.getDetails(),
                savedEvent.getTimestamp()
        );
    }
}