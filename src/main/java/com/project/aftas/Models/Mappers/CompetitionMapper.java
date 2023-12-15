package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.CompetitionDTO;
import com.project.aftas.Models.entities.Competition;
import com.project.aftas.Models.entities.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    CompetitionMapper INSTANCE = Mappers.getMapper(CompetitionMapper.class);

    @Mapping(target = "memberIds", source = "members")
    CompetitionDTO toDto(Competition competition);

    @Mapping(target = "members", source = "memberIds")
    Competition toEntity(CompetitionDTO competitionDTO);

    List<CompetitionDTO> toDtoList(List<Competition> competitions);

    default List<Long> mapMembersToIds(Set<Member> members) {
        return members.stream().map(Member::getId).collect(Collectors.toList());
    }

    default Set<Member> mapIdsToMembers(List<Long> memberIds) {
        return memberIds.stream().map(id -> Member.builder().id(id).build()).collect(Collectors.toSet());
    }
}
