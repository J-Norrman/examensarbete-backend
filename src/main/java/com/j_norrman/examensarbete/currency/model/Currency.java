package com.j_norrman.examensarbete.currency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "currency")
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currencyTypeName;

    private Double chaosEquivalent;

    private String icon;

    private Long currencyId;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
