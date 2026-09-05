package com.skillbridge.skillbridge_backend.service;

import com.skillbridge.skillbridge_backend.dto.StudentProfileRequest;
import com.skillbridge.skillbridge_backend.entity.Student;
import com.skillbridge.skillbridge_backend.entity.User;
import com.skillbridge.skillbridge_backend.repository.StudentRepository;
import com.skillbridge.skillbridge_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository,
                          UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public Student createProfile(StudentProfileRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (studentRepository.existsByUser_UserId(request.getUserId())) {
            throw new RuntimeException("Student profile already exists");
        }

        Student student = new Student();

        student.setUser(user);
        student.setName(request.getName());
        student.setEducation(request.getEducation());
        student.setBranch(request.getBranch());
        student.setGraduationYear(request.getGraduationYear());

        return studentRepository.save(student);
    }
}