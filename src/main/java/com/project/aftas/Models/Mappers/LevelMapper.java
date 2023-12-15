package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.LevelDTO;
import com.project.aftas.Models.entities.Level;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface LevelMapper {

    LevelMapper INSTANCE = Mappers.getMapper(LevelMapper.class);

    LevelDTO toDto(Level level);

    Level toEntity(LevelDTO levelDTO);
}
