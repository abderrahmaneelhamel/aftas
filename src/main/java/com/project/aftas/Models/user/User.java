package com.project.aftas.Models.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.aftas.Models.token.Token;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  public User(Integer id, String name, String email, String password, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;


  @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public Role getRole() {
    return this.role;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
