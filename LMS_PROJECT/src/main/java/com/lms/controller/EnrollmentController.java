package com.lms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.ApiResponse;
import com.lms.dto.EnrollmentResponseDTO;
import com.lms.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

	private final EnrollmentService service;

	public EnrollmentController(EnrollmentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse> enroll(@RequestParam Long studentId, @RequestParam Long courseId) {

		String msg = service.enrollStudent(studentId, courseId);

		return ResponseEntity.ok(new ApiResponse(msg, 200));
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<List<EnrollmentResponseDTO>> getByStudent(@PathVariable Long studentId) {

	    return ResponseEntity.ok(service.getEnrollmentsByStudent(studentId));
	}

	// ✅ ADD THIS (for cancel enrollment)
	@PutMapping("/cancel/{enrollmentId}")
	public ResponseEntity<ApiResponse> cancel(@PathVariable Long enrollmentId) {

	    String msg = service.cancelEnrollment(enrollmentId);

	    return ResponseEntity.ok(new ApiResponse(msg, 200));
	}
}