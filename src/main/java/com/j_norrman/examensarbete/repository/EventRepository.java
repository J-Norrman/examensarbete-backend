package com.j_norrman.examensarbete.repository;

import com.j_norrman.examensarbete.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
