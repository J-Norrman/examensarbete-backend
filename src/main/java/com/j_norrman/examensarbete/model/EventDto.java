package com.j_norrman.examensarbete.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String eventType;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
