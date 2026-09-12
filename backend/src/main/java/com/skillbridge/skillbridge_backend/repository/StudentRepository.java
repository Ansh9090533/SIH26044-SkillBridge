package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser_UserId(Long userId);

    boolean existsByUser_UserId(Long userId);

}