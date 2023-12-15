package com.project.aftas.Models.DTOs;

import lombok.Data;

@Data
public class LevelDTO {
    private Long id;
    private String code;
    private String description;
    private Integer points;
}