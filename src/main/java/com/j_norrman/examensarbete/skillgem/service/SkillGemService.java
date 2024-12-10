package com.j_norrman.examensarbete.skillgem.service;

import com.j_norrman.examensarbete.skillgem.model.SkillGemDto;
import com.j_norrman.examensarbete.skillgem.model.SkillGemResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillGemService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<SkillGemDto> fetchSkillGems() {
        String league = "Settlers";
        String url = "https://poe.ninja/api/data/itemoverview?league="+league+"&type=SkillGem";
        System.out.println("url = "+url);
        SkillGemResponse response = restTemplate.getForObject(url, SkillGemResponse.class);
        if (response == null || response.lines == null) {
            System.out.println("list null");
            return List.of();

        }
        return response.lines.stream()
                .map(item -> new SkillGemDto(
                        item.getId(),
                        item.getName(),
                        item.getIcon(),
                        item.getExplicitModifiers()
                ))
                .collect(Collectors.toList());
    }
}
