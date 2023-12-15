package com.project.aftas.Controllers;

import com.project.aftas.Models.entities.Hunting;
import com.project.aftas.Services.HuntingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hunting")
public class HuntingController {

    @Autowired
    private HuntingService huntingService;

    @PostMapping
    public ResponseEntity<Hunting> createHunting(@RequestBody Hunting hunting) {
        Hunting createdHunting = huntingService.createHunting(hunting);
        return new ResponseEntity<>(createdHunting, HttpStatus.CREATED);
    }

    @GetMapping("/for-member/{memberId}")
    public ResponseEntity<List<Hunting>> getHuntingByMemberId(@PathVariable Long memberId) {
        List<Hunting> huntingEntries = huntingService.getHuntingByMemberId(memberId);
        return new ResponseEntity<>(huntingEntries, HttpStatus.OK);
    }
}
