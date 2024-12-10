package com.j_norrman.examensarbete.skillgem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillGemDto {
    private Long id;
    private String name;
    private String icon;
    private List<Modifier> explicitModifiers;

}
