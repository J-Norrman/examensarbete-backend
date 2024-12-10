package com.j_norrman.examensarbete.skillgem.controller;

import com.j_norrman.examensarbete.skillgem.model.SkillGem;
import com.j_norrman.examensarbete.skillgem.model.SkillGemDto;
import com.j_norrman.examensarbete.skillgem.service.SkillGemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skill-gems")
public class SkillGemController {
    @Autowired
    private final SkillGemService skillGemService;

    public SkillGemController(SkillGemService skillGemService) {
        this.skillGemService = skillGemService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<SkillGemDto>> fetchSkillGem() {
        System.out.println("fetching data from api");
        try {
            List<SkillGemDto> gems = skillGemService.fetchSkillGems();
            return new ResponseEntity<>(gems,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
