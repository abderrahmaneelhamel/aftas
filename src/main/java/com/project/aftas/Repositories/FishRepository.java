package com.project.aftas.Repositories;

import com.project.aftas.Models.entities.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FishRepository extends JpaRepository<Fish, Long> {
}