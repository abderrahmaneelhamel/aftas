package com.project.aftas.Services;

import com.project.aftas.Models.DTOs.MemberDTO;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.Mappers.MemberMapper;
import com.project.aftas.Repositories.CompetitionRepository;
import com.project.aftas.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CompetitionRepository  competitionRepository;

    @Autowired
    private MemberMapper memberMapper;

    public Member createMember(MemberDTO memberDTO) {
        Member member = memberMapper.toEntity(memberDTO);
        Set<Competition> existingCompetitions = member.getCompetitions().stream()
                .map(competition -> {
                    try {
                        return competitionRepository.findById(competition.getId())
                                .orElseThrow(ChangeSetPersister.NotFoundException::new);
                    } catch (ChangeSetPersister.NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        member.setCompetitions(existingCompetitions);
        System.out.println(member);
        System.out.println("hello dto: "+memberMapper.toDto(member));
        return memberRepository.save(member);
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public List<Member> searchMembers(String searchTerm) {
        return memberRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(searchTerm, searchTerm);
    }

    public Member registerForMemberInCompetition(Long memberId, Long competitionId) {
        Member member = getMemberById(memberId);
        if (member != null) {
            Competition competition = new Competition();
            competition.setId(competitionId);
            member.getCompetitions().add(competition);
            return memberRepository.save(member);
        }
        return null;
    }
}
