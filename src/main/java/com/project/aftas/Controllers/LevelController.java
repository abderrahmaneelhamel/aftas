package com.project.aftas.Controllers;

import com.project.aftas.Models.entities.Level;
import com.project.aftas.Services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @PostMapping
    public ResponseEntity<Level> createLevel(@RequestBody Level level) {
        Level createdLevel = levelService.createLevel(level);
        return new ResponseEntity<>(createdLevel, HttpStatus.CREATED);
    }

    @GetMapping("/{levelId}")
    public ResponseEntity<Level> getLevelById(@PathVariable Long levelId) {
        Level level = levelService.getLevelById(levelId);
        return ResponseEntity.of(Optional.ofNullable(level));
    }

    @GetMapping
    public List<Level> getAllLevels() {
        return levelService.getAllLevels();
    }
}
