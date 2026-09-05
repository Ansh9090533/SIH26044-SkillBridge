package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    Optional<Student> findByUser_UserId(Integer userId);
    
    boolean existsByUser_UserId(Integer userId);

}