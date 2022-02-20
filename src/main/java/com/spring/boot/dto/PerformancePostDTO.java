package com.spring.boot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PerformancePostDTO {

	@NotNull
	@Min(value=1)
	private Integer managerId;
	
	@NotNull
	@Min(value=1)
	private Integer employeeId;
	
	//@NotNull
	@Min(value=1)
	private Integer evaluationId; //it is optional, just in case an employee has more than one evaluation in the evaluation table
	
	@NotNull
	@NotBlank
	@Size(max=256)
	private String name;
	
	@NotNull
	@NotBlank
	@Size(max=256)
	private String description;

	
	
	
	
	public Integer getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Integer evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
