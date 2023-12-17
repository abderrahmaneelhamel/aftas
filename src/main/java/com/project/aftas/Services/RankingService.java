package com.project.aftas.Services;

import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.entities.Ranking;
import com.project.aftas.Repositories.CompetitionRepository;
import com.project.aftas.Repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private CompetitionRepository competitionRepository;

    public Ranking createRanking(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    public void updatePointsForFishCaught(Member member, Competition competition, Integer pointsForFishCaught) {
        Ranking ranking = rankingRepository.findByMemberAndCompetition(member, competition);

        if (ranking != null) {
            // If the ranking already exists, update the points
            ranking.setPoints(ranking.getPoints() + pointsForFishCaught);
            rankingRepository.save(ranking);
        } else {
            // If the ranking doesn't exist, create a new one
            Ranking newRanking = Ranking.builder()
                    .member(member)
                    .competition(competition)
                    .points(pointsForFishCaught)
                    .build();
            rankingRepository.save(newRanking);
        }

        // After updating or creating the ranking, assign ranks for the competition
        assignRanksForCompetition(competition.getId());
    }


    public void assignRanksForCompetition(Long competitionId) {
        List<Ranking> rankings = rankingRepository.findByCompetitionId(competitionId, Sort.by(Sort.Order.desc("points")));

        int rank = 1;
        for (Ranking ranking : rankings) {
            ranking.setRank(rank++);
            rankingRepository.save(ranking);
        }
    }


    public List<Ranking> findMembersWithHighestScore(Long competitionId) {
        return rankingRepository.findByCompetitionId(competitionId, Sort.by(Sort.Order.asc("rank")));
    }
}
