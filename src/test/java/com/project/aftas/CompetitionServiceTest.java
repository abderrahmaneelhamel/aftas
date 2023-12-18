package com.project.aftas;

import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Fish;
import com.project.aftas.Models.entities.Member;
import com.project.aftas.Models.DTOs.CompetitionDTO;
import com.project.aftas.Models.Mappers.CompetitionMapper;
import com.project.aftas.Repositories.CompetitionRepository;
import com.project.aftas.Repositories.HuntingRepository;
import com.project.aftas.Services.CompetitionService;
import com.project.aftas.Services.FishService;
import com.project.aftas.Services.MemberService;
import com.project.aftas.Services.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitionServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private RankingService rankingService;

    @Mock
    private FishService fishService;

    @Mock
    private HuntingRepository huntingRepository;

    @Mock
    private CompetitionMapper competitionMapper;

    @InjectMocks
    private CompetitionService competitionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCompetition_shouldCreateAndSaveCompetition() {
        // Arrange
        CompetitionDTO competitionDTO = new CompetitionDTO();
        Competition competition = new Competition();
        when(competitionMapper.toEntity(competitionDTO)).thenReturn(competition);
        when(competitionRepository.save(competition)).thenReturn(competition);

        // Act
        Competition createdCompetition = competitionService.createCompetition(competitionDTO);

        // Assert
        assertNotNull(createdCompetition);
        assertEquals(competition, createdCompetition);
        verify(competitionRepository, times(1)).save(competition);
    }

    @Test
    void getCompetitionById_shouldReturnCompetitionIfExists() {
        // Arrange
        Long competitionId = 1L;
        Competition competition = new Competition();
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));

        // Act
        Competition retrievedCompetition = competitionService.getCompetitionById(competitionId);

        // Assert
        assertNotNull(retrievedCompetition);
        assertEquals(competition, retrievedCompetition);
    }

    @Test
    void getCompetitionById_shouldReturnNullIfNotExists() {
        // Arrange
        Long competitionId = 1L;
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.empty());

        // Act
        Competition retrievedCompetition = competitionService.getCompetitionById(competitionId);

        // Assert
        assertNull(retrievedCompetition);
    }
    @Test
    void getAllMembersInCompetition_shouldReturnMembers() {
        // Arrange
        Long competitionId = 1L;
        Set<Member> members = new HashSet<>(Arrays.asList(new Member(), new Member()));
        when(competitionRepository.findMembersByCompetitionId(competitionId)).thenReturn(members);

        // Act
        Set<Member> retrievedMembers = competitionService.getAllMembersInCompetition(competitionId);

        // Assert
        assertNotNull(retrievedMembers);
        assertEquals(members, retrievedMembers);
    }

    @Test
    void deleteCompetition_shouldDeleteIfExist() {
        // Arrange
        Long competitionId = 1L;
        Competition competition = new Competition();
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));

        // Act
        competitionService.deleteCompetition(competitionId);

        // Assert
        verify(competitionRepository, times(1)).delete(competition);
    }

    @Test
    void deleteCompetition_shouldDoNothingIfNotExists() {
        // Arrange
        Long competitionId = 1L;
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.empty());

        // Act
        competitionService.deleteCompetition(competitionId);

        // Assert
        verify(competitionRepository, never()).delete(any());
    }

    @Test
    void isCompetitionOngoing_shouldReturnTrueForOngoingCompetition() {
        // Arrange
        Date currentDate = new Date();
        Competition ongoingCompetition = new Competition();
        ongoingCompetition.setDate(currentDate);

        // Act
        boolean result = competitionService.isCompetitionOngoing(ongoingCompetition);

        // Assert
        assertTrue(result);
    }

    @Test
    void isCompetitionOngoing_shouldReturnFalseForNonOngoingCompetition() {
        // Arrange
        Date currentDate = new Date();
        Competition nonOngoingCompetition = new Competition();
        nonOngoingCompetition.setDate(new Date(currentDate.getTime() - 86400000)); // Set date to yesterday

        // Act
        boolean result = competitionService.isCompetitionOngoing(nonOngoingCompetition);

        // Assert
        assertFalse(result);
    }

    @Test
    void registerMemberInCompetition_shouldRegisterMember() {
        // Arrange
        Long memberId = 1L;
        Long competitionId = 2L;
        Member member = new Member();
        Competition competition = new Competition();
        when(memberService.getMemberById(memberId)).thenReturn(member);
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(competitionRepository.save(competition)).thenReturn(competition);

        // Act
        Competition registeredCompetition = competitionService.registerMemberInCompetition(memberId, competitionId);

        // Assert
        assertNotNull(registeredCompetition);
        assertTrue(registeredCompetition.getMembers().contains(member));
        verify(competitionRepository, times(1)).save(competition);
    }

    @Test
    void registerMemberInCompetition_shouldReturnNullIfMemberNotFound() {
        // Arrange
        Long memberId = 1L;
        Long competitionId = 2L;
        when(memberService.getMemberById(memberId)).thenReturn(null);

        // Act
        Competition registeredCompetition = competitionService.registerMemberInCompetition(memberId, competitionId);

        // Assert
        assertNull(registeredCompetition);
        verify(competitionRepository, never()).save(any());
    }

    @Test
    void registerMemberInCompetition_shouldReturnNullIfCompetitionNotFound() {
        // Arrange
        Long memberId = 1L;
        Long competitionId = 2L;
        Member member = new Member();
        when(memberService.getMemberById(memberId)).thenReturn(member);
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.empty());

        // Act
        Competition registeredCompetition = competitionService.registerMemberInCompetition(memberId, competitionId);

        // Assert
        assertNull(registeredCompetition);
        verify(competitionRepository, never()).save(any());
    }

    @Test
    void updatePointsForFishCaught_shouldUpdatePointsAndHunting() {
        // Arrange
        Long memberId = 1L;
        Long competitionId = 2L;
        Long fishId = 3L;

        Member member = new Member();
        Competition competition = new Competition();
        Fish fish = new Fish();
        int pointsForFishCaught = 10;

        when(memberService.getMemberById(memberId)).thenReturn(member);
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(fishService.getFishById(fishId)).thenReturn(fish);
        when(fishService.getPointsForFishLevel(fish)).thenReturn(pointsForFishCaught);
        when(huntingRepository.findByMemberAndCompetitionAndFish(member, competition, fish)).thenReturn(null);

        // Act
        competitionService.updatePointsForFishCaught(memberId, competitionId, fishId);

        // Assert
        verify(huntingRepository, times(1)).save(any()); // Ensure hunting entry is saved
        verify(rankingService, times(1)).updatePointsForFishCaught(member, competition, pointsForFishCaught);
    }
}