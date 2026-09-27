package com.lms.service;

import java.util.List;

import com.lms.dto.EnrollmentResponseDTO;

public interface EnrollmentService {

    String enrollStudent(Long studentId, Long courseId);
    List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId);
    String cancelEnrollment(Long enrollmentId);
}