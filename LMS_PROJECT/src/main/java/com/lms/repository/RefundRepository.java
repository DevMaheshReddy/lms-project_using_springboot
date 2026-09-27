package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.entity.Refund;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {

	List<Refund> findByEnrollmentId(Long enrollmentId);
}