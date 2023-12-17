package com.project.aftas.Models.DTOs;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class LevelDTO {
    private Long id;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Points are required")
    @PositiveOrZero(message = "Points must be non-negative")
    private Integer points;
}
