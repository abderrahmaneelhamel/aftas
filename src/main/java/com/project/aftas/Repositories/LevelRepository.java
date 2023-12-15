package com.project.aftas.Repositories;

import com.project.aftas.Models.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface LevelRepository extends JpaRepository<Level, Long> {

}
