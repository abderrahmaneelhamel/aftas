package com.project.aftas.Models.DTOs;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
public class CompetitionDTO {
    private Long id;

    @NotBlank(message = "Code is required")
    private String code;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be in the present or future")
    private Date date;

    @NotNull(message = "Start time is required")
    private Time startTime;

    @NotNull(message = "End time is required")
    private Time endTime;

    @Positive(message = "Number of participants must be positive")
    private int numberOfParticipants;

    @NotEmpty(message = "At least one member ID is required")
    private List<Long> memberIds;

    @NotBlank(message = "Location is required")
    private String location;
}
