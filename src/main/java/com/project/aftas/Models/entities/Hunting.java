package com.project.aftas.Models.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hunting")
public class Hunting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Competition competition;

    @ManyToOne(optional = false)
    private Fish fish;

    private int numberOfFish;

}
