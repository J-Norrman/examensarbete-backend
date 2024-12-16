package com.j_norrman.examensarbete.skillgem.service;

import com.j_norrman.examensarbete.skillgem.model.SkillGem;
import com.j_norrman.examensarbete.skillgem.model.SkillGemDto;
import com.j_norrman.examensarbete.skillgem.model.SkillGemResponse;
import com.j_norrman.examensarbete.skillgem.repository.SkillGemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillGemService {

    private final SkillGemRepository skillGemRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public SkillGemService(SkillGemRepository skillGemRepository) {
        this.skillGemRepository = skillGemRepository;
    }

    public List<SkillGemDto> fetchSkillGems(String league) {
        String url = "https://poe.ninja/api/data/itemoverview?league="+league+"&type=SkillGem";
        System.out.println("url = "+url);
        SkillGemResponse response = restTemplate.getForObject(url, SkillGemResponse.class);
        if (response == null || response.lines == null) {
            System.out.println("list null");
            return List.of();
        }
        return response.lines.stream()
                .map(item -> new SkillGemDto(item.getId(), item.getName(), item.getIcon(), item.getCategory(), item.getExplicitModifiers()
                ))
                .collect(Collectors.toList());
    }

    public void saveCategorizedGems(List<SkillGemDto> allGems) {
        Set<String> savedGemNames = new HashSet<>();
        for (SkillGemDto dto : allGems) {
            if (dto.getName() == null) continue;
            if (savedGemNames.contains(dto.getName())) {
                continue;
            }

            SkillGem gem = new SkillGem();
            gem.setName(dto.getName());
            gem.setIcon(dto.getIcon());
            gem.setExplicitModifiers(dto.getExplicitModifiers());

            String gemNameLower = dto.getName().toLowerCase();
            if (gemNameLower.contains("awakened")) {
                gem.setCategory("awakened");
            } else if (gemNameLower.contains("support")) {
                gem.setCategory("support");
            } else if (gemNameLower.contains("vaal")) {
                gem.setCategory("vaal");
            } else {
                gem.setCategory("active");
            }
            System.out.println("Saving gem: " + gem.getName() + " - " + gem.getIcon() + " - " + gem.getCategory());
            skillGemRepository.save(gem);
            savedGemNames.add(dto.getName());
        }
    }
    public void fetchAndSaveGems(String league) {
        List<SkillGemDto> allGems = fetchSkillGems(league);
        saveCategorizedGems(allGems);
        List<SkillGem> savedGems = skillGemRepository.findAll();
        System.out.println("Gems in DB: " + savedGems.size());
        //savedGems.forEach(g -> System.out.println(g.getName() + " - " + g.getCategory()));
    }

    @Transactional
    public Page<SkillGem> getAllGems(Pageable pageable){
        return skillGemRepository.findAll(pageable);
    }

    @Deprecated
    public Map<String,List<SkillGemDto>> categorizeGems(List<SkillGemDto> allGems){
        List<SkillGemDto> awakenedGems = new ArrayList<>();
        List<SkillGemDto> supportGems = new ArrayList<>();
        List<SkillGemDto> vaalGems = new ArrayList<>();
        List<SkillGemDto> activeGems = new ArrayList<>();
        Set<String> vaalGemNames = new HashSet<>();
        Set<String> awakenedGemNames = new HashSet<>();
        Set<String> supportGemNames = new HashSet<>();
        Set<String> activeGemNames = new HashSet<>();

        for (SkillGemDto gem : allGems) {
            if (gem.getName() == null) {
                continue;
            }
            String gemNameLower = gem.getName().toLowerCase();
            if (gemNameLower.contains("awakened")) {
                if (!awakenedGemNames.contains(gem.getName())) {
                    awakenedGems.add(gem);
                    awakenedGemNames.add(gem.getName());
                }
            }
            else if (gemNameLower.contains("support")) {
                if (!supportGemNames.contains(gem.getName())) {
                    supportGems.add(gem);
                    supportGemNames.add(gem.getName());
                }
            }else if (gemNameLower.contains("vaal")) {
                if (!vaalGemNames.contains(gem.getName())) {
                    vaalGems.add(gem);
                    vaalGemNames.add(gem.getName());
                }}
            else{
                if (!activeGemNames.contains(gem.getName())) {
                    activeGems.add(gem);
                    activeGemNames.add(gem.getName());
                }}}
        Map<String, List<SkillGemDto>> categorized = new HashMap<>();
        categorized.put("awakenedGems", awakenedGems);
        categorized.put("supportGems", supportGems);
        categorized.put("vaalGems", vaalGems);
        categorized.put("activeGems", activeGems);
        return categorized;
    }
}
