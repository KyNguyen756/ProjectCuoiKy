package com.example.ProjectCuoiKy.repository;

import com.example.ProjectCuoiKy.models.manga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface mangaRepository extends JpaRepository<manga, Long> {
    Optional<manga> findById(Long id);
}
