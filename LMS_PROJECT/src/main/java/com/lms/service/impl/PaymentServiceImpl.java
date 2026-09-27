package com.lms.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.dto.PaymentRequestDTO;
import com.lms.dto.PaymentResponseDTO;
import com.lms.entity.Enrollment;
import com.lms.entity.Payment;
import com.lms.enums.EnrollmentStatus;
import com.lms.enums.PaymentStatus;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.PaymentRepository;
import com.lms.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepo;
	private final EnrollmentRepository enrollmentRepo;

	public PaymentServiceImpl(PaymentRepository paymentRepo, EnrollmentRepository enrollmentRepo) {
		this.paymentRepo = paymentRepo;
		this.enrollmentRepo = enrollmentRepo;
	}

	
	@Override
	public String makePayment(PaymentRequestDTO dto) {

	    Enrollment enrollment = enrollmentRepo.findById(dto.getEnrollmentId())
	            .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

	    // ✅ Allow only PENDING
	    if (enrollment.getStatus() != EnrollmentStatus.PENDING) {
	        throw new RuntimeException("Payment not allowed in current state");
	    }

	    Double fee = enrollment.getCourse().getFee(); // later replace with courseFee

	    if (dto.getAmount() == null || dto.getAmount() <= 0) {
	        throw new RuntimeException("Invalid amount");
	    }

	    Double totalPaid = paymentRepo.getTotalPaid(dto.getEnrollmentId());
	    if (totalPaid == null) {
	        totalPaid = 0.0;
	    }

	    // ❗ Over payment check
	    if (totalPaid + dto.getAmount() > fee) {
	        throw new RuntimeException("Overpayment not allowed");
	    }

	    Payment payment = new Payment();
	    payment.setEnrollment(enrollment);
	    payment.setAmount(dto.getAmount());
	    payment.setPaymentDate(LocalDateTime.now());

	    Double newTotal = totalPaid + dto.getAmount();

	 // 🔥 Payment → Enrollment linking (as discussed)
	 if (newTotal >= fee) {
	     payment.setStatus(PaymentStatus.PAID);
	 } else {
	     payment.setStatus(PaymentStatus.PARTIAL);
	     enrollment.setStatus(EnrollmentStatus.PENDING);
	 }

	 paymentRepo.save(payment);

	// 🔥 ADD THIS BLOCK (after saving payment)
	Enrollment savedEnrollment = enrollmentRepo.findById(dto.getEnrollmentId())
	        .orElseThrow(() -> new RuntimeException("Enrollment not found"));

	if (payment.getStatus() == PaymentStatus.PAID) {
	    savedEnrollment.setStatus(EnrollmentStatus.ACTIVE);
	    enrollmentRepo.save(savedEnrollment);
	}
	    return payment.getStatus() == PaymentStatus.PAID
	            ? "Payment completed"
	            : "Partial payment done. Remaining: " + (fee - newTotal);
	}
	@Override
	public List<PaymentResponseDTO> getPaymentsByEnrollment(Long enrollmentId) {

	    List<Payment> payments = paymentRepo.findByEnrollmentId(enrollmentId);

	    return payments.stream()
	            .map(p -> new PaymentResponseDTO(
	                    p.getId(),
	                    p.getAmount(),
	                    p.getStatus().name(),
	                    p.getPaymentDate()
	            ))
	            .toList();
	}
}