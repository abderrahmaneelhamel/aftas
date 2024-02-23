package com.project.aftas.Repositories;

import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.entities.Ranking;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    @Query("SELECT r FROM Ranking r WHERE r.EmbeddedId.competition.id = :competitionId ORDER BY r.points DESC")
    List<Ranking> findByCompetitionId(Long competitionId, Sort sort);

    @Query("SELECT r FROM Ranking r WHERE r.EmbeddedId.member.id = :memberId AND r.EmbeddedId.competition.id = :competitionId")
    Ranking findByMemberAndCompetition(Member member, Competition competition);

//    @Modifying
//    @Query("UPDATE Ranking r SET r.points = r.points + :pointsForFishCaught WHERE r.id = :rankingId")
//    void updatePointsForFishCaught(@Param("rankingId") Long rankingId, @Param("pointsForFishCaught") Integer pointsForFishCaught);
}
