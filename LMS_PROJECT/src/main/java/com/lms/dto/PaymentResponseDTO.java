package com.lms.dto;

import java.time.LocalDateTime;

public class PaymentResponseDTO {

	private Long paymentId;
	private Double amount;
	private String status;
	private LocalDateTime date;

	public PaymentResponseDTO(Long paymentId, Double amount, String status, LocalDateTime date) {
		this.paymentId = paymentId;
		this.amount = amount;
		this.status = status;
		this.date = date;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public Double getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getDate() {
		return date;
	}
}