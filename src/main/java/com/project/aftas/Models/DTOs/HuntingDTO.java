package com.project.aftas.Models.DTOs;

import lombok.Data;

@Data
public class HuntingDTO {
    private Long id;
    private Long memberId;
    private Long competitionId;
    private Long fishId;
    private Integer numberOfFish;
}