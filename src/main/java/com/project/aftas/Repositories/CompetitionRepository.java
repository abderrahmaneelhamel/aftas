package com.project.aftas.Repositories;

import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Query("SELECT c.members FROM Competition c WHERE c.id = :competitionId")
    Set<Member> findMembersByCompetitionId(@Param("competitionId") Long competitionId);

}