package com.project.aftas.Services;

import com.project.aftas.Models.DTOs.FishDTO;
import com.project.aftas.Models.Mappers.FishMapper;
import com.project.aftas.Models.entities.Fish;
import com.project.aftas.Models.entities.Level;
import com.project.aftas.Repositories.FishRepository;
import com.project.aftas.Repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FishService {

    private final FishRepository fishRepository;
    private final LevelRepository levelRepository;
    private final FishMapper fishMapper;
    private final LevelService levelService;

    @Autowired
    public FishService(FishRepository fishRepository, LevelRepository levelRepository, FishMapper fishMapper, LevelService levelService) {
        this.fishRepository = fishRepository;
        this.levelRepository = levelRepository;
        this.fishMapper = fishMapper;
        this.levelService = levelService;
    }

    public Fish createFish(FishDTO fishDTO) {
        Fish fish = fishMapper.toEntity(fishDTO);
        Level existingLevel = levelRepository.findById(fish.getLevel().getId())
                .orElseThrow(() -> new RuntimeException("Level not found with ID: " + fish.getLevel().getId()));
        fish.setLevel(existingLevel);
        return fishRepository.save(fish);
    }

    public Fish getFishById(Long fishId) {
        return fishRepository.findById(fishId).orElse(null);
    }

    public List<Fish> getAllFish() {
        return fishRepository.findAll();
    }

    public int getPointsForFishLevel(Fish fish) {
        Level level = levelService.getLevelById(fish.getLevel().getId());
        return (level != null) ? level.getPoints() : 0;
    }
}
