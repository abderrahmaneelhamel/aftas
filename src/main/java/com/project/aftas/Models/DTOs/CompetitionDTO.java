package com.project.aftas.Models.DTOs;

import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class CompetitionDTO {
    private Long id;
    private String code;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int numberOfParticipants;
    private List<Long> memberIds;
    private String location;
}