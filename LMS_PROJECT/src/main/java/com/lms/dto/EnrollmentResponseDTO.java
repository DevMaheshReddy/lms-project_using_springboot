package com.lms.dto;

public class EnrollmentResponseDTO {

	private Long enrollmentId;
	private String studentName;
	private String courseTitle;
	private String status;

	public EnrollmentResponseDTO(Long enrollmentId, String studentName, String courseTitle, String status) {
		this.enrollmentId = enrollmentId;
		this.studentName = studentName;
		this.courseTitle = courseTitle;
		this.status = status;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public String getStatus() {
		return status;
	}
}