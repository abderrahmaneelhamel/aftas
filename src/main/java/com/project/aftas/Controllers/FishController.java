package com.project.aftas.Controllers;

import com.project.aftas.Models.DTOs.FishDTO;
import com.project.aftas.Models.entities.Fish;
import com.project.aftas.Services.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/fish")
public class FishController {

    @Autowired
    private FishService fishService;

    @PostMapping
    public ResponseEntity<Fish> createFish(@RequestBody FishDTO fishDTO) {
        System.out.println(fishDTO);
        Fish createdFish = fishService.createFish(fishDTO);
        return new ResponseEntity<>(createdFish, HttpStatus.CREATED);
    }

    @GetMapping("/{fishId}")
    public ResponseEntity<Fish> getFishById(@PathVariable Long fishId) {
        Fish fish = fishService.getFishById(fishId);
        return ResponseEntity.of(Optional.ofNullable(fish));
    }

    @GetMapping
    public List<Fish> getAllFish() {
        return fishService.getAllFish();
    }

    @PostMapping("/checkWeight/{fishId}")
    public ResponseEntity<String> checkFishWeight(@PathVariable Long fishId, @RequestParam Double weight) {
        Fish fish = fishService.getFishById(fishId);

        if (fish == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isAccepted = fishService.isFishWeightAccepted(fish, weight);

        if (isAccepted) {
            return ResponseEntity.ok("Accepted: Fish weight is within or above the average range.");
        } else {
            return ResponseEntity.ok("Rejected: Fish weight is below the average range.");
        }
    }
}
