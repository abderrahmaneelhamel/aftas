package com.project.aftas.Services;

import com.project.aftas.Models.entities.Hunting;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Repositories.HuntingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuntingService {

    @Autowired
    private HuntingRepository huntingRepository;

    public List<Hunting> getHuntingByMemberId(Long memberId) {
        Member member = new Member();
        member.setId(memberId);
        return huntingRepository.findByMember(member);
    }
    public Hunting createHunting(Hunting hunting) {
        return huntingRepository.save(hunting);
    }
}
