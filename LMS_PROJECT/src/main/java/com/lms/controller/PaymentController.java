package com.lms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.ApiResponse;
import com.lms.dto.PaymentRequestDTO;
import com.lms.dto.PaymentResponseDTO;
import com.lms.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService service;

	public PaymentController(PaymentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse> pay(@Valid @RequestBody PaymentRequestDTO dto) {

		String msg = service.makePayment(dto);

		return ResponseEntity.ok(new ApiResponse(msg, 200));
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<List<PaymentResponseDTO>> getPayments(@PathVariable Long enrollmentId) {

	    return ResponseEntity.ok(service.getPaymentsByEnrollment(enrollmentId));
	}
}