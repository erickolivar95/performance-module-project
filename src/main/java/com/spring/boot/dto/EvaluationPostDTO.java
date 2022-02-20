package com.spring.boot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EvaluationPostDTO {

	@NotNull
	@Min(value=1)
	private Integer managerId;
	
	@NotNull
	@Min(value=1)
	private Integer employeeId;
	
	@NotNull
	@NotBlank
	@Size(max=256)
	private String comment;
	
	@NotNull
	@Min(value=1)
	private Integer monthsPeriod;
	
	private boolean conditionTest; //this condition is just for testing, so that we can add evaluations of hard skill, soft skill and commitment, with test porpuses, in a real-world application this will be removed 
	
	

	
	
	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getMonthsPeriod() {
		return monthsPeriod;
	}

	public void setMonthsPeriod(Integer monthsPeriod) {
		this.monthsPeriod = monthsPeriod;
	}

	public boolean getConditionTest() {
		return conditionTest;
	}

	public void setConditionTest(boolean conditionTest) {
		this.conditionTest = conditionTest;
	}
	
	
	
	
	
}
