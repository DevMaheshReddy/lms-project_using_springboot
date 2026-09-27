package com.lms.dto;

import jakarta.validation.constraints.*;

public class PaymentRequestDTO {

	@NotNull(message = "Enrollment ID is required")
	private Long enrollmentId;

	@NotNull(message = "Amount is required")
	@Min(value = 1, message = "Amount must be greater than 0")
	private Double amount;

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}