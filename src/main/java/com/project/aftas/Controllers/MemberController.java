package com.project.aftas.Controllers;

import com.project.aftas.Exceptions.MemberAlreadyExistsException;
import com.project.aftas.Models.DTOs.MemberDTO;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.user.User;
import com.project.aftas.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<Page<Member>> getAllMembers(Pageable pageable) {
        Page<Member> members = memberService.getAllMembers(pageable);
        return ResponseEntity.ok(members);
    }
    @GetMapping("/jurys")
    public ResponseEntity<List<User>> getAllJurys() {
        List<User> jurys = memberService.getAllJurys();
        return ResponseEntity.ok(jurys);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberDTO memberDTO) {
        try {
            Member createdMember = memberService.createMember(memberDTO);
            return ResponseEntity.ok(createdMember);
        } catch (MemberAlreadyExistsException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An internal server error occurred: " + ex.getMessage());
        }
    }
}
