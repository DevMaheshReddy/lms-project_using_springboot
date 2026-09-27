package com.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.ApiResponse;
import com.lms.dto.StudentDTO;
import com.lms.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse> create(@RequestBody @Valid StudentDTO dto) {

		service.createStudent(dto);

		return ResponseEntity.ok(
				new ApiResponse("Student created successfully", 200)
		);
	}
}