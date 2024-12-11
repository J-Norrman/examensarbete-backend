package com.j_norrman.examensarbete.skillgem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "skill_gems")
public class SkillGem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;
    @ElementCollection
    @CollectionTable(name = "skill_gem_modifiers", joinColumns = @JoinColumn(name = "skill_gem_id"))
    private List<Modifier> explicitModifiers;
    private String category;
}
