package com.lms.service;

import com.lms.dto.StudentDTO;
import com.lms.entity.Student;

public interface StudentService {

    Student createStudent(StudentDTO dto);
}