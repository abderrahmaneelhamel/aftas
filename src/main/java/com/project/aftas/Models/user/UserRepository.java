package com.project.aftas.Models.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  @Query("SELECT new User(u.id,u.name,u.email,u.password,u.role) FROM User u WHERE u.role = 'JURY'")
  List<User> getJurys();
}
