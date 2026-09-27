package com.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.entity.Enrollment;
import com.lms.enums.EnrollmentStatus;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
	List<Enrollment> findByStudentId(Long studentId);
	long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status);
}