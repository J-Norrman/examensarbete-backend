package com.j_norrman.examensarbete.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "event_logs")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventType;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
