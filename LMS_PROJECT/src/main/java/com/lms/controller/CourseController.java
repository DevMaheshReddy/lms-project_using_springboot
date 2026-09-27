package com.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.ApiResponse;
import com.lms.dto.CourseDTO;
import com.lms.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService service;

	public CourseController(CourseService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody CourseDTO dto) {
		service.createCourse(dto);
		return ResponseEntity.ok(
				new ApiResponse("Course created successfully", 200)
		);
	}
}