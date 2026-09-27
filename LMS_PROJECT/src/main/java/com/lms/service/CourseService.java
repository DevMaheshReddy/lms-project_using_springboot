package com.lms.service;

import com.lms.dto.CourseDTO;
import com.lms.entity.Course;

public interface CourseService {

    Course createCourse(CourseDTO dto);
}