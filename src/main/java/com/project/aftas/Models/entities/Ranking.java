package com.project.aftas.Models.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ranking")
public class Ranking {

    @EmbeddedId
    private RankingEmbededId EmbeddedId;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int rank;

}
