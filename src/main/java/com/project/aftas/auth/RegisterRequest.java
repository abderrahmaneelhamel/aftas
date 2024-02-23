package com.project.aftas.auth;

import com.project.aftas.Models.entities.IdentificationDocumentType;
import com.project.aftas.Models.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  private String name;
  private String email;
  private String password;
  private Role role;
  private String membershipNumber;
  private String lastName;
  private String nationality;
  private String identityNumber;
  private IdentificationDocumentType identificationDocumentType;
  private Date membershipDate;
  private List<Long> competitionIds;


  public RegisterRequest(String name, String email, String password, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
