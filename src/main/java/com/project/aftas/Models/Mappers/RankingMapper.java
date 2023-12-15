package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.RankingDTO;
import com.project.aftas.Models.entities.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RankingMapper {

    RankingMapper INSTANCE = Mappers.getMapper(RankingMapper.class);

    RankingDTO toDto(Ranking ranking);

    Ranking toEntity(RankingDTO rankingDTO);
}
