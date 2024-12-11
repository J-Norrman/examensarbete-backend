package com.j_norrman.examensarbete.skillgem.repository;

import com.j_norrman.examensarbete.skillgem.model.SkillGem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillGemRepository extends JpaRepository<SkillGem, Long> {
    List<SkillGem> findSkillGemByName(String name);
    List<SkillGem> findSkillGemById(long id);
    List<SkillGem> findSkillGemByCategory(String category);
}
