package com.lms.dto;

import jakarta.validation.constraints.*;

public class CourseDTO {

	@NotBlank(message = "Title is required")
	private String title;

	@NotNull(message = "Fee is required")
	@Min(value = 1, message = "Fee must be greater than 0")
	private Double fee;
	    @NotNull(message = "Capacity is required")
	    @Min(value = 1, message = "Capacity must be at least 1")
	private Integer capacity;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
}