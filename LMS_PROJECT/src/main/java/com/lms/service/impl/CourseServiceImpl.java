package com.lms.service.impl;

import org.springframework.stereotype.Service;

import com.lms.dto.CourseDTO;
import com.lms.entity.Course;
import com.lms.repository.CourseRepository;
import com.lms.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository repo;

	public CourseServiceImpl(CourseRepository repo) {
		this.repo = repo;
	}

	@Override
	public Course createCourse(CourseDTO dto) {

		Course course = new Course();
		course.setTitle(dto.getTitle());
		course.setFee(dto.getFee());
        course.setCapacity(dto.getCapacity());
		return repo.save(course);
	}
}