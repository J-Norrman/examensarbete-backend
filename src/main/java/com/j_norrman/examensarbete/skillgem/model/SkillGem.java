package com.j_norrman.examensarbete.skillgem.model;

import lombok.Data;

import java.util.List;

@Data
public class SkillGem {
    private Long id;
    private String name;
    private String icon;
    private List<Modifier> explicitModifiers;
}
