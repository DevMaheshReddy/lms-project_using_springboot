package com.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.ApiResponse;
import com.lms.entity.Refund;
import com.lms.service.RefundService;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {

	private final RefundService refundService;

	public RefundController(RefundService refundService) {
		this.refundService = refundService;
	}

	@PostMapping("/{enrollmentId}")
	public ResponseEntity<ApiResponse> createRefund(@PathVariable Long enrollmentId) {

		refundService.createRefund(enrollmentId);

		return ResponseEntity.ok(
				new ApiResponse("Refund processed successfully", 200)
		);
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<List<Refund>> getRefunds(@PathVariable Long enrollmentId) {
		return ResponseEntity.ok(refundService.getRefundsByEnrollment(enrollmentId));
	}
}