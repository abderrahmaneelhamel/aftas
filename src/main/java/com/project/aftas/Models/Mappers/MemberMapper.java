package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.MemberDTO;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "competitionIds", source = "competitions")
    MemberDTO toDto(Member member);

    @Mapping(target = "competitions", source = "competitionIds")
    Member toEntity(MemberDTO memberDTO);

    List<MemberDTO> toDtoList(List<Member> members);

    default List<Long> mapCompetitionSetToLongList(Set<Competition> competitions) {
        return competitions.stream().map(Competition::getId).collect(Collectors.toList());
    }

    default Set<Competition> mapLongListToCompetitionSet(List<Long> competitionIds) {
        return competitionIds.stream().map(id -> Competition.builder().id(id).build()).collect(Collectors.toSet());
    }

    default List<Long> mapCompetitionListToLongList(List<Competition> competitions) {
        return competitions.stream().map(Competition::getId).collect(Collectors.toList());
    }

    default List<Competition> mapLongListToCompetitionList(List<Long> competitionIds) {
        return competitionIds.stream().map(id -> Competition.builder().id(id).build()).collect(Collectors.toList());
    }
}
