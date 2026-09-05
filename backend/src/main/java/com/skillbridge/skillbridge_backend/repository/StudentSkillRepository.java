package com.skillbridge.skillbridge_backend.repository;

import com.skillbridge.skillbridge_backend.entity.StudentSkill;
import com.skillbridge.skillbridge_backend.entity.StudentSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSkillRepository
        extends JpaRepository<StudentSkill, StudentSkillId> {

    List<StudentSkill> findByStudent_StudentId(Integer studentId);

    boolean existsByStudent_StudentIdAndSkill_SkillId(
            Integer studentId,
            Long skillId
    );
}