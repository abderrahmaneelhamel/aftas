package com.project.aftas.Services;

import com.project.aftas.Models.entities.Level;
import com.project.aftas.Repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public Level createLevel(Level level) {
        return levelRepository.save(level);
    }

    public Level getLevelById(Long levelId) {
        return levelRepository.findById(levelId).orElse(null);
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }
}
