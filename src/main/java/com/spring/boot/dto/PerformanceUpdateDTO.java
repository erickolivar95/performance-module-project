package com.spring.boot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PerformanceUpdateDTO {

	@NotNull
	@Min(value=1)
	private Integer managerId;
	
	@NotNull
	@Min(value=1)
	private Integer employeeId;
	
	@NotNull
	@NotBlank
	@Size(max=256)
	private String name;
	
	@NotNull
	@NotBlank
	@Size(max=256)
	private String description;

	
	
	

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
