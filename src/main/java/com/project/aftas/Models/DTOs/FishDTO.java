package com.project.aftas.Models.DTOs;

import lombok.Data;

@Data
public class FishDTO {
    private Long id;
    private String name;
    private Double averageWeight;
    private Long levelId;
}