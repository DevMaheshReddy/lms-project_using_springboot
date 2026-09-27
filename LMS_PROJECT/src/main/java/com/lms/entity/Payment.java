package com.lms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.lms.enums.PaymentStatus;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 🔥 CHANGED: OneToOne → ManyToOne
	@ManyToOne
	@JoinColumn(name = "enrollment_id")
	private Enrollment enrollment;

	private Double amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	private LocalDateTime paymentDate;

	public Long getId() {
		return id;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
}