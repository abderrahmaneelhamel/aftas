package com.project.aftas.Models.DTOs;

import lombok.Data;

@Data
public class RankingDTO {
    private Long id;
    private Long memberId;
    private Long competitionId;
    private Integer points;
    private Integer rank;
}