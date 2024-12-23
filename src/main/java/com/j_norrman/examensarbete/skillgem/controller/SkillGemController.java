package com.j_norrman.examensarbete.skillgem.controller;

import com.j_norrman.examensarbete.skillgem.model.SkillGem;
import com.j_norrman.examensarbete.skillgem.model.SkillGemDto;
import com.j_norrman.examensarbete.skillgem.service.SkillGemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<SkillGemDto> allGems = skillGemService.fetchSkillGems("Settlers");
            return new ResponseEntity<>(allGems,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/store")
    public ResponseEntity<Void> storeSkillGem(@RequestParam String league) {
        System.out.println("store data from api");
        try{
            skillGemService.fetchAndSaveGems(league);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<Map<String,Object>> getAllGems(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "50") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SkillGem> gemPage = skillGemService.getAllGems(pageable);
            Page<SkillGemDto> gemDtoPage = gemPage.map(gem -> new SkillGemDto(
                    gem.getId(),
                    gem.getName(),
                    gem.getIcon(),
                    gem.getCategory(),
                    gem.getExplicitModifiers()
            ));

            Map<String, Object> response = new HashMap<>();
            response.put("content", gemDtoPage.getContent());
            response.put("page", gemDtoPage.getNumber());
            response.put("size", gemDtoPage.getSize());
            response.put("totalPages", gemDtoPage.getTotalPages());
            response.put("totalElements", gemDtoPage.getTotalElements());
            response.put("hasNext", gemDtoPage.hasNext());
            response.put("hasPrevious", gemDtoPage.hasPrevious());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
