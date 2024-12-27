package com.j_norrman.examensarbete.eventhandlerMarkedForRemoval.repository;

import com.j_norrman.examensarbete.eventhandlerMarkedForRemoval.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface EventRepository extends JpaRepository<Event, Long> {
}
