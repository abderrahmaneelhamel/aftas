package com.project.aftas.Models.DTOs;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class FishDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Average weight is required")
    @Positive(message = "Average weight must be positive")
    private Double averageWeight;

    @NotNull(message = "Level ID is required")
    private Long levelId;
}
