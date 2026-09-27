package com.lms.service.impl;

import org.springframework.stereotype.Service;

import com.lms.dto.StudentDTO;
import com.lms.entity.Student;
import com.lms.repository.StudentRepository;
import com.lms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repo;

	public StudentServiceImpl(StudentRepository repo) {
		this.repo = repo;
	}

	@Override
	public Student createStudent(StudentDTO dto) {

		Student student = new Student();
		student.setName(dto.getName());
		student.setEmail(dto.getEmail());

		return repo.save(student);
	}
}