package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}