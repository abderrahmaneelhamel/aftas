package com.project.aftas.Repositories;

import com.project.aftas.Models.entities.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Hunting;
import com.project.aftas.Models.entities.Member;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    @Query("SELECT h FROM Hunting h WHERE h.member = :member AND h.competition = :competition AND h.fish = :fish")
    Hunting findByMemberAndCompetitionAndFish(Member member, Competition competition, Fish fish);

    @Query("SELECT h FROM Hunting h WHERE h.member = :member")
    List<Hunting> findByMember(Member member);
}