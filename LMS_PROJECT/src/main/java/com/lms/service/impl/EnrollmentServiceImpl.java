package com.lms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.dto.EnrollmentResponseDTO;
import com.lms.entity.*;
import com.lms.enums.EnrollmentStatus;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.*;
import com.lms.service.EnrollmentService;

import jakarta.transaction.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final PaymentRepository paymentRepo;

    public EnrollmentServiceImpl(StudentRepository studentRepo,
                                 CourseRepository courseRepo,
                                 EnrollmentRepository enrollmentRepo,
                                 PaymentRepository paymentRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.paymentRepo = paymentRepo;
    }

    // =========================
    // ENROLL STUDENT
    // =========================
    @Override
    @Transactional
    public String enrollStudent(Long studentId, Long courseId) {

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
     // 🔥 CAPACITY CHECK
        if (course.getCapacity() == null) {
            throw new RuntimeException("Course capacity not set");
        }

        long activeCount = enrollmentRepo.countByCourseIdAndStatus(courseId, EnrollmentStatus.ACTIVE);

        if (activeCount >= course.getCapacity()) {
            throw new RuntimeException("Course is full");
        }
        Optional<Enrollment> existing = enrollmentRepo
                .findByStudentIdAndCourseId(studentId, courseId);

        if (existing.isPresent()) {

            Enrollment enrollment = existing.get();

            if (enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                throw new RuntimeException("Student already enrolled in this course");
            }

            if (enrollment.getStatus() == EnrollmentStatus.PENDING) {
                return "Already enrolled. Please complete payment.";
            }

            if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
                enrollment.setStatus(EnrollmentStatus.PENDING);
                enrollmentRepo.save(enrollment);
                return "Re-enrollment successful. Please proceed to payment.";
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.PENDING);

        enrollmentRepo.save(enrollment);

        return "Enrollment created. Please proceed to payment.";
    }

    // =========================
    // GET ENROLLMENTS BY STUDENT
    // =========================
    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId) {

        List<Enrollment> enrollments = enrollmentRepo.findByStudentId(studentId);

        return enrollments.stream()
                .map(e -> new EnrollmentResponseDTO(
                        e.getId(),
                        e.getStudent().getName(),
                        e.getCourse().getTitle(),
                        e.getStatus().name()
                ))
                .toList();
    }

    // =========================
    // CANCEL ENROLLMENT (REFUND LOGIC)
    // =========================
    @Override
    public String cancelEnrollment(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

     // ❌ Already cancelled
        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new RuntimeException("Enrollment already cancelled");
        }

        // ❌ Only ACTIVE can be cancelled
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new RuntimeException("Only ACTIVE enrollment can be cancelled");
        }
        List<Payment> payments = paymentRepo.findByEnrollmentId(enrollmentId);

        Double totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(0.0, Double::sum);

        Double fee = enrollment.getCourse().getFee();

        Double refundAmount;

        if (totalPaid == 0) {
            refundAmount = 0.0;
        } else if (totalPaid < fee) {
            refundAmount = totalPaid * 0.5; // 50% refund
        } else {
            refundAmount = totalPaid * 0.8; // 80% refund
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepo.save(enrollment);

        return "Enrollment cancelled. Refund eligible: " + refundAmount;
    }
}