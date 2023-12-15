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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Competition competition;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int rank;

}
