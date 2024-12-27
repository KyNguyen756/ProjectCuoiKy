package com.example.ProjectCuoiKy.repository;

import com.example.ProjectCuoiKy.models.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface usersRepository extends JpaRepository<users, Long> {
    Optional<users> findByUsername(String username);
    Optional<users> findByEmail(String email);
}
