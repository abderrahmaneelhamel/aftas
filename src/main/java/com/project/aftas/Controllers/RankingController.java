package com.project.aftas.Controllers;

import com.project.aftas.Models.entities.Ranking;
import com.project.aftas.Services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/highest-score/{competitionId}")
    public ResponseEntity<List<Ranking>> getMembersWithHighestScore(@PathVariable Long competitionId) {
        List<Ranking> membersWithHighestScore = rankingService.findMembersWithHighestScore(competitionId);

        if (membersWithHighestScore.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(membersWithHighestScore, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ranking> createRanking(@RequestBody Ranking ranking) {
        Ranking createdRanking = rankingService.createRanking(ranking);
        return new ResponseEntity<>(createdRanking, HttpStatus.CREATED);
    }
}
