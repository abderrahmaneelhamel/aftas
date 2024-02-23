package com.project.aftas.Models.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RankingEmbededId implements Serializable {

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Competition competition;

}
