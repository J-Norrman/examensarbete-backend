package com.j_norrman.examensarbete.eventhandler.repository;

import com.j_norrman.examensarbete.eventhandler.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
