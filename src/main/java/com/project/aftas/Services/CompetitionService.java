package com.project.aftas.Services;

import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Fish;
import com.project.aftas.Models.entities.Hunting;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Repositories.CompetitionRepository;
import com.project.aftas.Models.DTOs.CompetitionDTO;
import com.project.aftas.Models.Mappers.CompetitionMapper;
import com.project.aftas.Repositories.HuntingRepository;
import com.project.aftas.Repositories.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final MemberRepository  memberRepository;
    private final MemberService memberService;
    private final RankingService rankingService;
    private final FishService fishService;
    private final HuntingRepository huntingRepository;
    private final CompetitionMapper competitionMapper;

    @Autowired
    public CompetitionService(CompetitionRepository competitionRepository,
                              MemberRepository memberRepository,
                              MemberService memberService,
                              RankingService rankingService,
                              FishService fishService,
                              HuntingRepository huntingRepository,
                              CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.rankingService = rankingService;
        this.fishService = fishService;
        this.huntingRepository = huntingRepository;
        this.competitionMapper = competitionMapper;
    }


    public Competition createCompetition(CompetitionDTO competitionDTO) {
        Competition competition = competitionMapper.toEntity(competitionDTO);

        if (competition.getMembers() == null) {
            competition.setMembers(new HashSet<>());
        }

        Set<Member> existingMembers = competition.getMembers().stream()
                .map(member -> memberRepository.findById(member.getId())
                        .orElseThrow(() -> new RuntimeException("Member not found with ID: " + member.getId())))
                .collect(Collectors.toSet());

        competition.setMembers(existingMembers);

        return competitionRepository.save(competition);
    }



    public Competition getCompetitionEntityById(Long competitionId) {
        return competitionRepository.findById(competitionId).orElse(null);
    }

    public Competition getCompetitionById(Long competitionId) {
        return getCompetitionEntityById(competitionId);
    }

    public Page<Competition> getAllCompetitions(Pageable pageable) {
        return competitionRepository.findAll(pageable);
    }

    @Transactional
    public Set<Member> getAllMembersInCompetition(Long competitionId) {
        return competitionRepository.findMembersByCompetitionId(competitionId);
    }

    public void deleteCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            competitionRepository.delete(competition);
        }
    }

    public List<CompetitionDTO> getOngoingCompetitions() {
        List<Competition> allCompetitions = competitionRepository.findAll();

        List<Competition> ongoingCompetitions = allCompetitions.stream()
                .filter(this::isCompetitionOngoing)
                .collect(Collectors.toList());

        return competitionMapper.toDtoList(ongoingCompetitions);
    }

    public boolean isCompetitionOngoing(Competition competition) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate = dateFormat.format(currentDate);
        String formattedCompetitionDate = dateFormat.format(competition.getDate());
        return formattedCurrentDate.equals(formattedCompetitionDate);
    }


    public Competition registerMemberInCompetition(Long memberId, Long competitionId) {
        Member member = memberService.getMemberById(memberId);
        if (member != null) {
            Competition competition = competitionRepository.findById(competitionId).orElse(null);
            if (competition != null) {
                Set<Member> members = competition.getMembers();
                if (members == null) {
                    members = new HashSet<>(); // Initialize the set if it's null
                    competition.setMembers(members);
                }
                members.add(member);
                return competitionRepository.save(competition);
            }
        }
        return null;
    }

    public void updatePointsForFishCaught(Long memberId, Long competitionId, Long fishId) {
        Member member = memberService.getMemberById(memberId);
        if (member != null) {
            Competition competition = competitionRepository.findById(competitionId).orElse(null);
            Fish fish = fishService.getFishById(fishId);
            int pointsForFishCaught = fishService.getPointsForFishLevel(fish);

            if (competition != null) {
                // Check if the Hunting entry already exists for the same Member, Competition, and Fish
                Hunting existingHunting = huntingRepository.findByMemberAndCompetitionAndFish(member, competition, fish);

                if (existingHunting != null) {
                    // If it exists, update the number of fish
                    existingHunting.setNumberOfFish(existingHunting.getNumberOfFish() + 1);
                    huntingRepository.save(existingHunting);
                } else {
                    // If it doesn't exist, create a new Hunting entry
                    Hunting newHunting = Hunting.builder()
                            .member(member)
                            .competition(competition)
                            .fish(fish)
                            .numberOfFish(1)
                            .build();
                    huntingRepository.save(newHunting);
                }
                // Update points for the fish caught
                rankingService.updatePointsForFishCaught(member, competition, pointsForFishCaught);
            }
        }
    }


}
