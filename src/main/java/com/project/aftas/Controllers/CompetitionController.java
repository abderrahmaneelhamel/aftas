package com.project.aftas.Controllers;

import com.project.aftas.Models.DTOs.CompetitionDTO;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    public ResponseEntity<Competition> createCompetition(@RequestBody CompetitionDTO competitionDTO) {
        Competition createdCompetition = competitionService.createCompetition(competitionDTO);
        return new ResponseEntity<>(createdCompetition, HttpStatus.CREATED);
    }

    @GetMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetitionById(@PathVariable Long competitionId) {
        Competition competition = competitionService.getCompetitionById(competitionId);
        if (competition != null) {
            return ResponseEntity.ok(competition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Competition>> getAllCompetitions(Pageable pageable) {
        Page<Competition> competitions = competitionService.getAllCompetitions(pageable);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{competitionId}/members")
    public ResponseEntity<Set<Member>> getAllMembersInCompetition(@PathVariable Long competitionId) {
        Set<Member> members = competitionService.getAllMembersInCompetition(competitionId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<CompetitionDTO>> getOngoingCompetitions() {
        List<CompetitionDTO> ongoingCompetitions = competitionService.getOngoingCompetitions();
        return ResponseEntity.ok(ongoingCompetitions);
    }

    @PostMapping("/register-member")
    public ResponseEntity<Competition> registerMemberInCompetition(@RequestBody Map<String, Long> requestBody) {
        Long memberId = requestBody.get("memberId");
        Long competitionId = requestBody.get("competitionId");
        Competition registeredCompetition = competitionService.registerMemberInCompetition(memberId, competitionId);
        if (registeredCompetition != null) {
            return ResponseEntity.ok(registeredCompetition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update-points")
    public ResponseEntity<String> updatePointsForFishCaught(@RequestBody Map<String, Long> requestBody) {
        Long memberId = requestBody.get("memberId");
        Long competitionId = requestBody.get("competitionId");
        Long fishId = requestBody.get("fishId");
        competitionService.updatePointsForFishCaught(memberId, competitionId, fishId);
        return ResponseEntity.ok("Points updated successfully");
    }

    @DeleteMapping("/{competitionId}")
    public ResponseEntity<String> deleteCompetition(@PathVariable Long competitionId) {
        competitionService.deleteCompetition(competitionId);
        return ResponseEntity.ok("Competition deleted successfully");
    }
}
