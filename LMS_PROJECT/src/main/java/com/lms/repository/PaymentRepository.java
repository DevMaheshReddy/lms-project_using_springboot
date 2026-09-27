package com.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.lms.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 🔥 Total paid amount
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.enrollment.id = :enrollmentId")
    Double getTotalPaid(@Param("enrollmentId") Long enrollmentId);
    List<Payment> findByEnrollmentId(Long enrollmentId);
}