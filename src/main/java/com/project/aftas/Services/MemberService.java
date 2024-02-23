package com.project.aftas.Services;

import com.project.aftas.Models.DTOs.MemberDTO;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.Mappers.MemberMapper;
import com.project.aftas.Models.user.Role;
import com.project.aftas.Models.user.User;
import com.project.aftas.Models.user.UserRepository;
import com.project.aftas.Repositories.CompetitionRepository;
import com.project.aftas.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CompetitionRepository  competitionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public MemberService(UserRepository userRepository, PasswordEncoder passwordEncoder,MemberRepository memberRepository, CompetitionRepository competitionRepository, MemberMapper memberMapper){
        this.memberRepository = memberRepository;
        this.competitionRepository = competitionRepository;
        this.memberMapper = memberMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        var user = User.builder()
                .name(member.getFirstName())
                .email(member.getEmail())
                .password(passwordEncoder.encode("password"))
                .role(Role.MEMBER)
                .build();
        userRepository.save(user);
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

    public List<User> getAllJurys() {
        List<User> jurys = userRepository.getJurys();
        System.out.println(jurys);
        return jurys;
    }
}
