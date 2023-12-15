package com.project.aftas.Models.Mappers;

import com.project.aftas.Models.DTOs.FishDTO;
import com.project.aftas.Models.entities.Fish;
import com.project.aftas.Models.entities.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FishMapper {

    FishMapper INSTANCE = Mappers.getMapper(FishMapper.class);

    @Mapping(target = "level.id", source = "levelId")
    Fish toEntity(FishDTO fishDTO);

    @Mapping(target = "levelId", source = "level.id")
    FishDTO toDto(Fish fish);

    default List<Long> mapFishToIds(List<Fish> fishList) {
        return fishList.stream().map(Fish::getId).collect(Collectors.toList());
    }

    default List<Level> mapIdsToLevels(List<Long> levelIds) {
        return levelIds.stream().map(id -> Level.builder().id(id).build()).collect(Collectors.toList());
    }

    default List<Long> mapLevelsToIds(List<Level> levels) {
        return levels.stream().map(Level::getId).collect(Collectors.toList());
    }
}
