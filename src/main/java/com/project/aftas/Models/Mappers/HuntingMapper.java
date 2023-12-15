package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.HuntingDTO;
import com.project.aftas.Models.entities.Hunting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HuntingMapper {

    HuntingMapper INSTANCE = Mappers.getMapper(HuntingMapper.class);

    HuntingDTO toDto(Hunting hunting);

    Hunting toEntity(HuntingDTO huntingDTO);
}
