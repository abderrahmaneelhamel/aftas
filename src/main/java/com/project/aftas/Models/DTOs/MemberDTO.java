package com.project.aftas.Models.DTOs;

import com.project.aftas.Models.entities.IdentificationDocumentType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;

    @NotBlank(message = "Membership number is required")
    private String membershipNumber;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Identity number is required")
    private String identityNumber;

    @NotNull(message = "Identification document type is required")
    private IdentificationDocumentType identificationDocumentType;

    @NotNull(message = "Membership date is required")
    @PastOrPresent(message = "Membership date must be in the past or present")
    private Date membershipDate;

    @NotEmpty(message = "At least one competition ID is required")
    private List<Long> competitionIds;
}
