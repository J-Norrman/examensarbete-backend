package com.j_norrman.examensarbete.skillgem.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Modifier {
    private String text;
    private boolean optional;
}
